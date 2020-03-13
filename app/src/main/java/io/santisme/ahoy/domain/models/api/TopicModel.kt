package io.santisme.ahoy.domain.models.api

import com.google.gson.annotations.SerializedName

data class TopicModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("posts_count") val postsCount: Int,
    @SerializedName("views") val views: Int,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("created_at") val createdAt: String
//    @SerializedName("posters") val posters: List<Poster>
)
