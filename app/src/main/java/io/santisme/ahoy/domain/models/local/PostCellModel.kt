package io.santisme.ahoy.domain.models.local

import android.widget.ImageView
import io.santisme.ahoy.sources.main.topicdetail.adapter.TopicDetailHolderProtocol

data class PostCellModel(
    val avatarTemplate: String?,
    val userImage: ImageView?,
    val username: String,
    val postContent: String,
    val updatedAt: String
): TopicDetailHolderProtocol