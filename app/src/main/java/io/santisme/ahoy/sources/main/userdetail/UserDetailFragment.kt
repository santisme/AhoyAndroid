package io.santisme.ahoy.sources.main.userdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.UserDetailModel
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.toolbar_topics.*

class UserDetailFragment() : Fragment() {

    private lateinit var delegate: UserDetailFragmentDelegate

    companion object {
        fun newInstance() =
            UserDetailFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserDetailFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${UserDetailFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        delegate.requestLoggedUser()
        buttonLogOut.setOnClickListener { onLogOutClicked() }
    }

    private fun onLogOutClicked() {
        delegate.onLogOutClicked()
    }

    private fun requestUserAvatar(avatarTemplate: String, into: ImageView) {
        activity?.applicationContext?.let {
            Glide.with(it)
                .load(
                    "https://${BuildConfig.DiscourseDomain}${avatarTemplate.replace(
                        "{size}",
                        "64"
                    )}"
                )
                .placeholder(R.drawable.user_avatar)
                .centerCrop()
                .into(into)
        }
    }

    fun updatePrivateMessages(count: Int) {
        labelPrivateMessagesCountContent.text = count.toString()
    }

    fun updateUserDetailModel(userDetailModel: UserDetailModel) {
        userDetailModel.avatarTemplate?.let {
            requestUserAvatar(avatarTemplate = it, into = userAvatar)
        }

        if (userDetailModel.moderator != null && userDetailModel.moderator) {
            activity?.applicationContext?.let {
                labelModerator.setTextColor(
                    ContextCompat.getColor(
                        it,
                        R.color.colorAhoyRuby
                    )
                )
            }
        } else {
            activity?.applicationContext?.let {

                labelModerator.setTextColor(
                    ContextCompat.getColor(
                        it,
                        R.color.colorAhoyGray
                    )
                )
            }
        }

        labelNameContent.text = userDetailModel.name
        labelContentNickname.text = userDetailModel.username
        labelLastConnectionContent.text = userDetailModel.lastSeenAt
        labelPrivateMessagesCountContent.text = "0"
        buttonLogOut.isClickable = userDetailModel.logged
    }
}

interface UserDetailFragmentDelegate {
    fun requestLoggedUser()
    fun onLogOutClicked()
}