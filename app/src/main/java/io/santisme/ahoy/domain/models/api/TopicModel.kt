package io.santisme.ahoy.domain.models.api

import com.google.gson.annotations.SerializedName

data class TopicModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("posts_count") val postsCount: Int,
    @SerializedName("views") val views: Int?,
    @SerializedName("category_id") val categoryId: Int?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("posters") val posters: List<PosterModel>?,
    @SerializedName("archetype") val archetype: String?,
//    @SerializedName("post_stream") val postStream: PostStreamModel?,
//    @SerializedName("details") val topicDetails: TopicDetailsModel?,
    @SerializedName("bumped") val bumped: Boolean?
)

data class PostStreamModel(
    @SerializedName("posts") val posts: List<PostModel>
)

enum class TopicArchetype(val topicArchetype: String) {
    Regular("regular"),
    PrivateMessage("private_message")
}

data class TopicDetailsModel(
    @SerializedName("created_by") val topicCreatedBy: UserModel
)