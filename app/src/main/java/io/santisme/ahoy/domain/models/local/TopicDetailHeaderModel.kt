package io.santisme.ahoy.domain.models.local

import io.santisme.ahoy.sources.main.topicdetail.adapter.TopicDetailHolderProtocol

data class TopicDetailHeaderModel(
    val topicTitle: String,
    val postsCount: Int,
    val viewCount: Int,
    val updatedAt: String,
    val topicContent: String,
    val posterList: List<PosterListModel>
): TopicDetailHolderProtocol