package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.models.api.TopicModel
import io.santisme.ahoy.domain.responses.PrivateMessagesByUserResponse
import retrofit2.Response

interface PrivateMessagesProtocol {
    suspend fun requestPrivateMessagesByUser(username: String): Response<PrivateMessagesByUserResponse>
    fun savePrivateMessages(topicList: List<TopicModel>)
}