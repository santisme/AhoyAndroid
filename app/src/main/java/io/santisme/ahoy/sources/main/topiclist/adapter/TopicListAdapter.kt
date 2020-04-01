package io.santisme.ahoy.sources.main.topiclist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicCellModel
import io.santisme.ahoy.sources.main.topiclist.viewholder.TopicHolder

class TopicListAdapter(
    private val topicClickListener: ((TopicCellModel) -> Unit)? = null
) : RecyclerView.Adapter<TopicHolder>() {

    private val topicList = mutableListOf<TopicCellModel>()

    private val listener: ((View) -> Unit) = {
        val topic = it.tag as TopicCellModel
        topicClickListener?.invoke(topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)

        return TopicHolder(view)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topicList[position]
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<TopicCellModel>) {
        this.topicList.clear()
        this.topicList.addAll(topics)
        notifyDataSetChanged()
    }

}