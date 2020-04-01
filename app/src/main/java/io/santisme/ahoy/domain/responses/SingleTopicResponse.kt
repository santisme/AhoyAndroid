package io.santisme.ahoy.domain.responses

import com.google.gson.annotations.SerializedName
import io.santisme.ahoy.domain.models.api.PostStreamModel
import io.santisme.ahoy.domain.models.api.TopicDetailsModel
import io.santisme.ahoy.domain.models.api.TopicModel

data class SingleTopicResponse(
    @SerializedName("post_stream") val postStream: PostStreamModel?,
    val id: Int,
    val title: String,
    val categoryId: Int?,
    val details: TopicDetailsModel?,
    val suggestedTopics: List<TopicModel>?,
    val views: Int
)