package io.santisme.ahoy.domain.responses

import com.google.gson.annotations.SerializedName

data class PasswordRecoveryResponse(
    @SerializedName("success") private val success: String,
    @SerializedName("user_found") private val userFound: Boolean
)