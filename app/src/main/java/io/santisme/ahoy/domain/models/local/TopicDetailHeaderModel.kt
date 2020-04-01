package io.santisme.ahoy.domain.models.local

import androidx.recyclerview.widget.RecyclerView

data class TopicDetailHeaderModel(
    val topicTitle: String,
    val postsCount: Int,
    val viewCount: Int,
    val updatedAt: String,
    val topicContent: String,
    val postersRecyclerView: RecyclerView
)