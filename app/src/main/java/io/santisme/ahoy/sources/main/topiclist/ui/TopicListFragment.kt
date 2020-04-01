package io.santisme.ahoy.sources.main.topiclist.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicCellModel
import io.santisme.ahoy.sources.main.topiclist.adapter.TopicListAdapter
import kotlinx.android.synthetic.main.fragment_topic_list.*

enum class TopicFilterType(val filter: String) {
    Top("Top topics"),
    Latest("Latest topics")
}

class TopicListFragment : Fragment() {

    private lateinit var delegate: TopicListFragmentDelegate
    private lateinit var topicsAdapter: TopicListAdapter
    private var topicFilterName: TopicFilterType = TopicFilterType.Latest

    companion object {
        fun newInstance() =
            TopicListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopicListFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${TopicListFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        topicsAdapter = TopicListAdapter {
            onTopicSelected(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    // MARK: - Private Methods
    private fun setupView() {
        delegate.requestTopics(topicFilterName = topicFilterName)

        listTopics.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = topicsAdapter
        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            delegate.onTopicListRefreshListener(topicFilterName = topicFilterName)
        }
    }

    private fun onTopicSelected(topic: TopicCellModel) {
        delegate.onTopicSelected(topic)
    }

    // MARK: -
    fun updateModel(topicList: List<TopicCellModel>) {
        topicsAdapter.setTopics(topicList)
    }


//    private fun retryButtonClicked() {
//        delegate.onRetryButtonClicked()
//
//    }

//    fun enableLoading(enabled: Boolean) {
//        viewRetry.visibility = View.INVISIBLE
//        if (enabled) {
//            listTopics.visibility = View.INVISIBLE
//            buttonCreate.hide()
//            viewLoading.visibility = View.VISIBLE
//        } else {
//            listTopics.visibility = View.VISIBLE
//            buttonCreate.show()
//            viewLoading.visibility = View.INVISIBLE
//        }
//    }

//    fun handleRequestError(requestError: RequestError) {
//        listTopics.visibility = View.INVISIBLE
//        buttonCreate.hide()
//        viewRetry.visibility = View.VISIBLE
//
//        val message = if (requestError.messageResId != null)
//            getString(requestError.messageResId)
//        else requestError.message ?: getString(R.string.error_request_default)
//
//        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
//    }

    //    private fun createTopicButtonClicked() {
//        delegate.onCreateTopicButtonClicked()
//    }
//    private fun requestUserAvatar(avatarTemplate: String) {
//        activity?.applicationContext?.let {
//            Glide.with(it)
//                .load(
//                    "https://${BuildConfig.DiscourseDomain}${avatarTemplate.replace(
//                        "{size}",
//                        "64"
//                    )}"
//                )
//                .placeholder(R.drawable.user_avatar)
//                .centerCrop()
//                .into(userImage)
//        }
//    }
}

interface TopicListFragmentDelegate {
    fun onTopicSelected(topic: TopicCellModel)
    fun onTopicListRefreshListener(topicFilterName: TopicFilterType)
    fun requestTopics(topicFilterName: TopicFilterType)
//    fun onSearchViewQuerySubmit(context: Context?, query: String?)
}