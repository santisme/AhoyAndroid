package io.santisme.ahoy.sources.main.topicdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.PostCellModel
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.sources.main.topicdetail.viewholder.PostHolder

class TopicDetailAdapter(
    private val postClickListener: ((PostCellModel) -> Unit)? = null
) : RecyclerView.Adapter<PostHolder>() {

    private val postList = mutableListOf<PostCellModel>()
    private lateinit var headerModel: TopicDetailHeaderModel

    enum class HolderType(val type: Int) {
        Header(0),
        Item(1)
    }

    private val listener: ((View) -> Unit) = {
        val topic = it.tag as PostCellModel
        postClickListener?.invoke(topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = when(viewType) {
            HolderType.Header.type -> inflateHeaderHolder(parent = parent)
            else -> inflatePostHolder(parent = parent)
        }
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postList[position]
        holder.post = post
        holder.itemView.setOnClickListener(listener)
    }

    private fun inflateHeaderHolder(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_header_topic, parent, false)
    }


    private fun inflatePostHolder(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    }

    private fun setTopicDetail(headerModel: TopicDetailHeaderModel, postList: List<PostCellModel>) {
        this.headerModel = headerModel
        this.postList.clear()
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }

}