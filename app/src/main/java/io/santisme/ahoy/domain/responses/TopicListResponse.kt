package io.santisme.ahoy.domain.responses

import com.google.gson.annotations.SerializedName
import io.santisme.ahoy.domain.models.api.TopicModel
import io.santisme.ahoy.domain.models.api.UserModel

typealias PrivateMessagesByUserResponse = TopicListResponse
typealias LatestTopicsResponse = TopicListResponse
typealias TopTopicsResponse = TopicListResponse

data class TopicListResponse(
    @SerializedName("users") val users: Array<UserModel>,
    @SerializedName("topic_list") val topicList: TopicList
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TopicListResponse

        if (!users.contentEquals(other.users)) return false
        if (topicList != other.topicList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = users.contentHashCode()
        result = 31 * result + topicList.hashCode()
        return result
    }
}

data class TopicList(
    @SerializedName("more_topics_url") val moreTopicsUrl: String?,
    @SerializedName("topics") val topics: Array<TopicModel>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TopicList

        if (moreTopicsUrl != other.moreTopicsUrl) return false

        return true
    }

    override fun hashCode(): Int {
        return moreTopicsUrl?.hashCode() ?: 0
    }
}
