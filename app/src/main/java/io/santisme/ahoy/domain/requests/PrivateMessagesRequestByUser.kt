package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.responses.PrivateMessagesByUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PrivateMessagesRequestByUser {
    @GET("topics/private-messages/{username}.json")
    suspend fun privateMessagesByUser(
        @Path(value = "username") username: String,
        @Header(value = "Api-Username") headerUsername: String?
    ): Response<PrivateMessagesByUserResponse>
}