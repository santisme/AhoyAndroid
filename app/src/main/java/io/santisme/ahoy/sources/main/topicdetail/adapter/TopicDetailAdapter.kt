package io.santisme.ahoy.sources.main.topicdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.PostCellModel
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.sources.main.topicdetail.viewholder.HeaderHolder
import io.santisme.ahoy.sources.main.topicdetail.viewholder.PostHolder

class TopicDetailAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val postList = mutableListOf<TopicDetailHolderProtocol>()

    enum class HolderType(val type: Int) {
        Header(0),
        Item(1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HolderType.Header.type -> HeaderHolder(inflateHeaderHolder(parent = parent))
            HolderType.Item.type -> PostHolder(inflatePostHolder(parent = parent))
            else -> throw RuntimeException("No match for $viewType.")
        }
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            HolderType.Header.type -> HolderType.Header.type
            else -> HolderType.Item.type
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder) {
            holder.header = postList[position] as TopicDetailHeaderModel
        } else if (holder is PostHolder) {
            holder.post = postList[position] as PostCellModel
        }
    }

    // MARK: - Public Methods
    fun setTopicDetail(postList: List<PostCellModel>, headerModel: TopicDetailHeaderModel) {
//        this.headerModel = headerModel
        this.postList.clear()
        this.postList.add(headerModel)
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }

    // MARK: - Private Methods
    private fun inflateHeaderHolder(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header_topic, parent, false)
    }

    private fun inflatePostHolder(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    }

}

interface TopicDetailHolderProtocol {}