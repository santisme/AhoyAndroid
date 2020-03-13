package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.models.local.SignUpModel
import io.santisme.ahoy.domain.responses.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface SignUpRequest {
    @POST("users")
    suspend fun signUp(@Body request: SignUpModel, @Header(value = "Api-Username") headerUsername: String?): Response<SignUpResponse>
}
