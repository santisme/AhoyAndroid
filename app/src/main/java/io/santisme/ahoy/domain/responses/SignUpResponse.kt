package io.santisme.ahoy.domain.responses

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("success") private val success: Boolean,
    @SerializedName("active") private val active: Boolean?,
    @SerializedName("message") private val message: String,
    @SerializedName("userId") private val userId: Int?
)