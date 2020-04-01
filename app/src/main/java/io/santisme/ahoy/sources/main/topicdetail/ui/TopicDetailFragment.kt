package io.santisme.ahoy.sources.main.topicdetail.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.PostCellModel
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.sources.main.topicdetail.adapter.TopicDetailAdapter
import kotlinx.android.synthetic.main.fragment_topic_detail.*
import kotlinx.android.synthetic.main.item_header_topic.*

const val TOPIC_DETAIL_FRAGMENT_TAG = "TOPIC_DETAIL_FRAGMENT"

class TopicDetailFragment(private val topicId: Int) : Fragment() {
    private lateinit var adapter: TopicDetailAdapter
    private lateinit var delegate: TopicDetailFragmentDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopicDetailFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${TopicDetailFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = TopicDetailAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postList.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            updateTopicDetail(topicId = topicId)
        }
    }

    override fun onResume() {
        super.onResume()
        updateTopicDetail(topicId)
    }

    // MARK: - Public Methods
    fun loadTopicDetailModelWith(postList: List<PostCellModel>, headerModel: TopicDetailHeaderModel) {
        adapter.setTopicDetail(postList = postList, headerModel = headerModel)
    }

    // MARK: - Private Methods
    private fun updateTopicDetail(topicId: Int) {
        delegate.updateTopicDetail(topicId = topicId)
    }

}

interface TopicDetailFragmentDelegate {
    fun updateTopicDetail(topicId: Int)
}