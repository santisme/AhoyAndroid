package io.santisme.ahoy.domain.models.api

import com.google.gson.annotations.SerializedName

data class PosterModel(
    @SerializedName("description") val description: String,
    @SerializedName("user_id") val userId: Int?
)