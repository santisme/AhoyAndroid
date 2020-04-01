package io.santisme.ahoy.sources.main.topicdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.santisme.ahoy.R
import io.santisme.ahoy.sources.main.topicdetail.adapter.TopicDetailAdapter
import kotlinx.android.synthetic.main.fragment_topic_detail.*

const val TOPIC_DETAIL_FRAGMENT_TAG = "TOPIC_DETAIL_FRAGMENT"

class TopicDetailFragment(private val topicId: Int) : Fragment() {
    lateinit var adapter: TopicDetailAdapter
//    var listener: PostsInteractionListener? = null
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is PostsInteractionListener)
//            listener = context
//    }

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

    }

}
