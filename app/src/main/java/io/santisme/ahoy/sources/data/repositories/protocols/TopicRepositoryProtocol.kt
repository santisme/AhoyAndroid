package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.requests.TopicsOrder
import io.santisme.ahoy.domain.responses.TopicListResponse
import retrofit2.Response

interface TopicRepositoryProtocol {
    suspend fun fetchLatestTopics(order: TopicsOrder? = TopicsOrder.ByDefault, ascending: Boolean? = false, page: Int? = 0): Response<TopicListResponse>
    suspend fun fetchTopTopics(): Response<TopicListResponse>
//    fun createNewTopic(title: String, raw: String, categoryId: Int?, createdAt: String, completion: @escaping (Result<NewTopicResponse, Error>) -> ())
}