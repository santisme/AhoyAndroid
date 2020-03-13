package io.santisme.ahoy.sources.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.UserModel
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import io.santisme.ahoy.sources.main.topiclist.TopicListFragment
import io.santisme.ahoy.sources.main.topiclist.TopicListFragmentDelegate
import io.santisme.ahoy.sources.main.userdetail.UserDetailFragment
import io.santisme.ahoy.sources.main.userdetail.UserDetailFragmentDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_detail.*

class MainActivity : AppCompatActivity(), MainActivityModelViewProtocol, TopicListFragmentDelegate,
    UserDetailFragmentDelegate {

    private val delegate: MainActivityDelegate by lazy {
        MainActivityModelView(
            view = this,
//            context = applicationContext,
            localRepository = LocalRepository(context = applicationContext)
        )
    }
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null
    private val tabIcons = intArrayOf(
        R.drawable.ic_home,
        R.drawable.ic_message,
        R.drawable.ic_user_detail
    )

    enum class FragmentName(val fragmentName: String) {
        HomeFragment("HOME_FRAGMENT"),
        PrivateMessagesFragment("PRIVATE_MESSAGES_FRAGMENT"),
        UserDetailFragment("USER_DETAIL_FRAGMENT")
    }

    companion object {
        data class TabLayoutFragment(
            val fragmentName: FragmentName,
            val fragment: Fragment
        )
    }

    private val fragments = mutableListOf<TabLayoutFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupView()
        }

    }

    override fun updateUserModel(userModel: UserModel) {
        userModel.avatarTemplate?.let {
            requestUserAvatar(avatarTemplate = it)
        }

        if (userModel.moderator != null && userModel.moderator) {
            labelModerator.setTextColor(resources.getColor(R.color.colorAhoyRuby))
        } else {
            labelModerator.setTextColor(resources.getColor(R.color.colorAhoyGray))
        }

        labelNameContent.text = userModel.name
        labelContentNickname.text = userModel.username
        labelLastConnectionContent.text = userModel.lastSeenAt
        labelPrivateMessagesCountContent.text = "0"
        buttonLogOut.isClickable = userModel.logged
    }

    override fun requestLoggedUser() {
        delegate.requestLoggedUser()
    }

    private fun setupView() {
        fragments.add(TabLayoutFragment(FragmentName.HomeFragment, TopicListFragment.newInstance()))
        fragments.add(TabLayoutFragment(FragmentName.PrivateMessagesFragment, Fragment()))
        fragments.add(
            TabLayoutFragment(
                FragmentName.UserDetailFragment,
                UserDetailFragment.newInstance()
            )
        )

        tabLayout = tabsMain
        viewPager = view_pager
        tabLayout?.let { tLayout ->
            viewPager?.let {
                it.adapter = MainPageViewAdapter(supportFragmentManager, lifecycle, fragments)
                TabLayoutMediator(
                    tLayout,
                    it,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        tab.setIcon(this.tabIcons[position])
                    }).attach()
            }
        }
        viewPager?.offscreenPageLimit = 1

    }

    private fun requestUserAvatar(avatarTemplate: String) {
        Glide.with(applicationContext)
            .load(
                "https://${BuildConfig.DiscourseDomain}${avatarTemplate.replace(
                    "{size}",
                    "64"
                )}"
            )
            .placeholder(R.drawable.user_avatar)
            .centerCrop()
            .into(userAvatar)
    }

}

interface MainActivityDelegate {
    fun requestLoggedUser()
}
