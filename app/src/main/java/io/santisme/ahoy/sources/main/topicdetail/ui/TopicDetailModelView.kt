package io.santisme.ahoy.sources.main.topicdetail.ui

import io.santisme.ahoy.domain.models.local.PostCellModel
import io.santisme.ahoy.domain.models.local.PosterListModel
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.domain.responses.SingleTopicResponse
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class TopicDetailModelView(
    private val view: TopicDetailModelViewProtocol?,
    private val localRepository: LocalRepository
) : TopicDetailActivityDelegate, CoroutineScope {

    private val job: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun updateTopicDetail(topicId: Int) {
        fetchSingleTopicAndHandleResponse(topicId = topicId)
    }

    // MARK: - Private Methods
    private fun fetchSingleTopicAndHandleResponse(topicId: Int) {
        val job = async {
            localRepository.fetchSingleTopic(topicId = topicId)
        }

        launch(Dispatchers.Main) {
            try {
                val response = job.await()

                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let {
                        updateTopicDetailModelWith(response = it)
                    }
                } else {
                    view?.showError(message = "Error fetching single topic")
                }
            } catch (error: HttpException) {
                error.printStackTrace()
                view?.showError(message = "Network connection error")

            } catch (error: IOException) {
                error.printStackTrace()
                view?.showError(message = "Network connection error")

            } catch (error: Throwable) {
                error.printStackTrace()
                view?.showError(message = "Ooops: Something went wrong ${error.message}")

            }
        }
    }

    private fun updateTopicDetailModelWith(response: SingleTopicResponse) {
        val postList = response.postStream?.posts
        val postCellModelList = mutableListOf<PostCellModel>()
        val posterList = mutableListOf<PosterListModel>()

        postList?.forEach {

            postCellModelList.add(
                PostCellModel(
                    avatarTemplate = it.avatarTemplate,
                    userImage = null,
                    username = it.username,
                    postContent = it.cooked ?: "",
                    updatedAt = it.updatedAt ?: ""
                )
            )

            posterList.add(
                PosterListModel(
                    avatarTemplate = it.avatarTemplate,
                    username = it.username
                )
            )
        }

        val headerModel = TopicDetailHeaderModel(
            topicTitle = response.title,
            postsCount = postList?.count() ?: 0,
            updatedAt = postList?.last()?.updatedAt ?: "",
            topicContent = postList?.first()?.cooked ?: "",
            posterList = posterList,
            viewCount = response.views
        )

        if (postCellModelList.count() > 0) {
            view?.updateTopicDetailModelWith(
                postList = postCellModelList,
                headerModel = headerModel
            )
        }
    }

}

interface TopicDetailModelViewProtocol {
    fun showError(message: String)
    fun updateTopicDetailModelWith(
        postList: List<PostCellModel>,
        headerModel: TopicDetailHeaderModel
    )
}