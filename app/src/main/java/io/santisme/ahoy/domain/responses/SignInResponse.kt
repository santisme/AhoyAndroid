package io.santisme.ahoy.domain.responses

import com.google.gson.annotations.SerializedName
import io.santisme.ahoy.domain.models.api.UserModel

data class SignInResponse(
    @SerializedName("user") val user: UserModel?,
    @SerializedName("error_type") val errorType: String?
)