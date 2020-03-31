package io.santisme.ahoy.sources.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicCellModel
import io.santisme.ahoy.domain.models.local.UserDetailModel
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import io.santisme.ahoy.sources.login.LoginActivity
import io.santisme.ahoy.sources.main.topicdetail.ui.EXTRA_TOPIC_ID
import io.santisme.ahoy.sources.main.topicdetail.ui.TOPIC_DETAIL_FRAGMENT_TAG
import io.santisme.ahoy.sources.main.topicdetail.ui.TopicDetailActivity
import io.santisme.ahoy.sources.main.topicdetail.ui.TopicDetailFragment
import io.santisme.ahoy.sources.main.topiclist.ui.TopicFilterType
import io.santisme.ahoy.sources.main.topiclist.ui.TopicListFragment
import io.santisme.ahoy.sources.main.topiclist.ui.TopicListFragmentDelegate
import io.santisme.ahoy.sources.main.userdetail.UserDetailFragment
import io.santisme.ahoy.sources.main.userdetail.UserDetailFragmentDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_topics.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityModelViewProtocol,
    TopicListFragmentDelegate,
    UserDetailFragmentDelegate {

    // MARK: - Private properties
    private val delegate: MainActivityDelegate by lazy {
        MainActivityModelView(
            view = this,
            context = applicationContext,
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
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    // MARK: - Private properties TopicListFragment
//    private var moreTopicsUrl: String? = null
//    private val pagingCellMargin: Int = 8
    private var topicCellModel: List<TopicCellModel>? = null

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
            setupDrawer()
        }

    }

    // MARK: - MainActivityModelViewProtocol protocol implementation
    override fun updateUserDetailModel(userDetailModel: UserDetailModel) {
        val userDetailFragment = fragments.elementAt(2).fragment as UserDetailFragment
        userDetailFragment.updateUserDetailModel(userDetailModel = userDetailModel)
    }

    override fun requestLoggedUser() {
        delegate.requestLoggedUser()
    }

    override fun onLogOutClicked() {
        delegate.onLogOutClicked()
    }

    override fun launchLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun updatePrivateMessages(count: Int) {
        val userDetailFragment = fragments.elementAt(2).fragment as UserDetailFragment
        userDetailFragment.updatePrivateMessages(count = count)
    }

    override fun showError(message: String) {
        Snackbar.make(view_pager, message, Snackbar.LENGTH_LONG).show()
    }

    override fun navigateToTopicDetail(topicId: Int) {
        val intent = Intent(this, TopicDetailActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topicId)
        startActivity(intent)
    }

    // MARK: - NavigationView.OnNavigationItemSelectedListener protocol implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_latest_topics -> delegate.onNavigationLatestTopicsItemSelected()
            R.id.action_top_topics -> delegate.onNavigationTopTopicsItemSelected()
        }
        drawer.closeDrawers()
        return super.onOptionsItemSelected(item)
    }

    // MARK: - TopicFragmentDelegate protocol implementation
    override fun onTopicSelected(topic: TopicCellModel) {
        delegate.onTopicSelected(topic = topic)
    }

    override fun onTopicListRefreshListener(topicFilterName: TopicFilterType) {
        delegate.onTopicListRefreshListener(topicFilterName = topicFilterName)
    }

    override fun requestTopics(topicFilterName: TopicFilterType) {
        delegate.requestTopics(topicFilterName = topicFilterName)
    }

    // MARK: - TopicListModelView protocol implementation
    override fun updateTopicList(model: List<TopicCellModel>) {
        topicCellModel = model
        val topicListFragment = fragments.elementAt(0).fragment as TopicListFragment
        topicListFragment.updateModel(topicList = model)
    }


    // MARK: - Private Methods
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

    private fun setupDrawer() {
        setSupportActionBar(toolbarTopicsToolbar)

        drawer = drawer_layout
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbarTopicsToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        // This method shows the burger symbol in the toolbar
        toggle.syncState()
        drawer.addDrawerListener(toggle)
        navigation_view.setNavigationItemSelectedListener(this)

//            toolbarTopicsSearchView.apply {
//
//                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String?): Boolean {
//                        topicViewModel.onSearchViewQuerySubmit(context = context, query = query)
//                        return true
//                    }
//
//                    override fun onQueryTextChange(newText: String?): Boolean {
//                        return true
//                    }
//
//                })
//                setOnCloseListener {
//                    topicViewModel.onSearchViewQuerySubmit(context = context, query = EMPTY)
//                    false
//                }
//            }
    }
}

interface MainActivityDelegate {
    // MARK: - UserDetailFragmentDelegate
    fun requestLoggedUser()
    fun onLogOutClicked()

    // MARK: - TopicFragmentDelegate
    fun onTopicSelected(topic: TopicCellModel)
    fun onTopicListRefreshListener(topicFilterName: TopicFilterType)
    fun requestTopics(topicFilterName: TopicFilterType)

    // MARK: - TopicFilterNavigationMenu
    fun onNavigationLatestTopicsItemSelected()
    fun onNavigationTopTopicsItemSelected()
}
