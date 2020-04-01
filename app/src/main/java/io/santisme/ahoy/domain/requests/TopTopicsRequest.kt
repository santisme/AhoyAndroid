package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.responses.TopTopicsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TopTopicsRequest {
    @GET("top.json")
    suspend fun fetchTopTopics(@Header(value = "Api-Username") headerUsername: String?): Response<TopTopicsResponse>
}