package io.santisme.ahoy.domain.models.local

import android.widget.ImageView

data class TopicCellModel(
    val id: Int,
    val userImage: ImageView?,
    val avatarTemplate: String?,
    val topicTitle: String,
    val postsCount: Int,
    val viewCount: Int,
    val createdAt: String
)