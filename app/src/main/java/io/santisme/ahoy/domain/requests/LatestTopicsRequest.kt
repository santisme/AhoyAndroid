package io.santisme.ahoy.domain.requests

import io.santisme.ahoy.domain.responses.LatestTopicsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LatestTopicsRequest {
    @GET("latest.json")

    suspend fun fetchLatestTopics(
        @Query(value = "order") order: TopicsOrder?,
        @Query(value = "ascending") ascending: Boolean?,
        @Query(value = "page") page: Int?,
        @Header(value = "Api-Username") headerUsername: String?
    ): Response<LatestTopicsResponse>
}

enum class TopicsOrder(val order: String) {
    ByDefault("default"),
    Created("created"),
    Activity("activity"),
    Views("views"),
    Posts("posts"),
    Category("category"),
    Likes("likes"),
    OpLikes("op_likes"),
    Posters("posters")
}
