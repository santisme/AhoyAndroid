package io.santisme.ahoy.sources.main.topicdetail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.santisme.ahoy.R
import kotlinx.android.synthetic.main.toolbar_posts.*

const val EXTRA_TOPIC_ID = "topic_id"

class TopicDetailActivity : AppCompatActivity() {

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

}
