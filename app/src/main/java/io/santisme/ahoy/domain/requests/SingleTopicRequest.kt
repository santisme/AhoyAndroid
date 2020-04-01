package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.responses.SingleTopicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SingleTopicRequest {
    @GET("t/{topicId}.json")
    suspend fun fetchSingleTopic(@Path(value = "topicId") topicId: Int, @Header(value = "Api-Username") headerUsername: String?): Response<SingleTopicResponse>
}