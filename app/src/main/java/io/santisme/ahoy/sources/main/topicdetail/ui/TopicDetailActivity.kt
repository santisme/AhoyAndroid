package io.santisme.ahoy.sources.main.topicdetail.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.api.PostModel
import io.santisme.ahoy.domain.models.local.PostCellModel
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_posts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val EXTRA_TOPIC_ID = "topic_id"

class TopicDetailActivity : AppCompatActivity(), TopicDetailFragmentDelegate,
    TopicDetailModelViewProtocol {

    private val delegate: TopicDetailActivityDelegate by lazy {
        TopicDetailModelView(
            view = this,
            localRepository = LocalRepository(context = applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        setSupportActionBar(toolbarPostsToolbar)

        // Enable back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val topicId = intent?.getIntExtra(EXTRA_TOPIC_ID, 0)

        topicId?.let {

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragmentContainer,
                        TopicDetailFragment(
                            it
                        ),
                        TOPIC_DETAIL_FRAGMENT_TAG
                    )
                    .commit()
            }

        } ?: run {
            throw IllegalArgumentException("You should provide an id for the topic")
        }

    }

    // Important to go back when back navigation button is clicked
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // MARK: - TopicDetailFragmentDelegate protocol implementation
    override fun updateTopicDetail(topicId: Int) {
        delegate.updateTopicDetail(topicId = topicId)
    }

    override fun showError(message: String) {
        Snackbar.make(view_pager, message, Snackbar.LENGTH_LONG).show()
    }

    override fun updateTopicDetailModelWith(
        postList: List<PostCellModel>,
        headerModel: TopicDetailHeaderModel
    ) {
        getTopicDetailFragmentIfAvailableOrNull()?.loadTopicDetailModelWith(postList = postList, headerModel = headerModel)
    }

    // MARK: - Private Methods
    private fun getTopicDetailFragmentIfAvailableOrNull(): TopicDetailFragment? {
        val currentFragment = supportFragmentManager.findFragmentByTag(TOPIC_DETAIL_FRAGMENT_TAG)
        return if (currentFragment != null && currentFragment.isVisible) {
            currentFragment as TopicDetailFragment
        } else {
            null
        }

    }

}

interface TopicDetailActivityDelegate {
    fun updateTopicDetail(topicId: Int)
}