package io.santisme.ahoy.domain.models.api

import com.google.gson.annotations.SerializedName

class PostModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_template") val avatarTemplate: String,
    @SerializedName("cooked") val cooked: String?,
    @SerializedName("blurb") val blurb: String?,
    @SerializedName("raw") val raw: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("topic_id") val topicId: Int
)
