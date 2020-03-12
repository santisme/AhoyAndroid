package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.responses.SignInResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SignInRequest {
    @GET("users/{username}.json")
    suspend fun signIn(@Path(value = "username") username: String, @Header(value = "Api-Username") headerUsername: String?): Response<SignInResponse>
}