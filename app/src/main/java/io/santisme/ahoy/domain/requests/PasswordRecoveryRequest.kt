package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.models.local.PasswordRecoveryModel
import io.santisme.ahoy.domain.responses.PasswordRecoveryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PasswordRecoveryRequest {
    @POST("session/forgot_password")
    suspend fun recoverPassword(@Body request: PasswordRecoveryModel, @Header(value = "Api-Username") headerUsername: String?): Response<PasswordRecoveryResponse>
}