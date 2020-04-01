package io.santisme.ahoy.sources.main

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicCellModel
import io.santisme.ahoy.domain.models.local.UserDetailModel
import io.santisme.ahoy.domain.requests.TopicsOrder
import io.santisme.ahoy.domain.responses.LatestTopicsResponse
import io.santisme.ahoy.sources.additions.TimeOffsetRepository
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import io.santisme.ahoy.sources.main.topiclist.ui.TopicFilterType
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivityModelView(
    private val view: MainActivityModelViewProtocol?,
    private val context: Context,
    private val localRepository: LocalRepository
) :
    MainActivityDelegate, CoroutineScope {

    private val job: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private lateinit var userDetailModel: UserDetailModel
    private val topicListCellModel = mutableListOf<TopicCellModel>()
    private var pageNumber = 0

    private fun requestPrivateMessagesByUser(username: String) {
        val job = async {
            localRepository.requestPrivateMessagesByUser(username = username)
        }

        launch(Dispatchers.Main) {
            try {
                val response = job.await()

//            view?.enableLoading(false)
                response.body().takeIf { it != null }?.let {
                    localRepository.savePrivateMessages(topicList = it.topicList.topics.toList())
                    view?.updatePrivateMessages(count = it.topicList.topics.count())
                }
                    ?: run { view?.showError(message = "Response is null") }

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

    private fun fetchLatestTopicsAndHandleResponse() {
        val job = async {
            localRepository.fetchLatestTopics(order = TopicsOrder.Created, page = pageNumber)
        }

        launch(Dispatchers.Main) {
            try {
                val response = job.await()

//                view?.enableLoading(false)
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let {
                        updateTopicListCellModelWith(response = it)
                    }
                } else {
                    view?.showError(message = "Error fetching latest topics")
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

    private fun fetchTopTopicsAndHandleResponse() {
        val job = async {
            localRepository.fetchTopTopics()
        }

        launch(Dispatchers.Main) {
            try {
                val response = job.await()

//                view?.enableLoading(false)
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }?.let {
                        updateTopicListCellModelWith(response = it)
                    }
                } else {
                    view?.showError(message = "Error fetching top topics")
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

    private fun updateTopicListCellModelWith(response: LatestTopicsResponse) {
        topicListCellModel.clear()
        response.topicList.topics.forEach { topic ->
            val topicId = topic.id
            val topicTitle = topic.title
            val postsCount = topic.postsCount
            val viewCount = topic.views ?: 0
            val createdAt = topic.createdAt
            val topicOwnerId = topic.posters?.first { poster ->
                poster.description.startsWith(prefix = "Original Poster", ignoreCase = true)
            }?.userId
            topicOwnerId?.let {

                val avatarTemplate =
                    response.users.first { user -> user.id == topicOwnerId }.avatarTemplate
                val userAvatar = ImageView(context)

                avatarTemplate?.let {
                    requestUserAvatar(avatarTemplate = avatarTemplate, into = userAvatar)
                }

                topicListCellModel.add(
                    TopicCellModel(
                        id = topicId,
                        userImage = userAvatar,
                        avatarTemplate = avatarTemplate,
                        topicTitle = topicTitle,
                        postsCount = postsCount,
                        viewCount = viewCount,
                        createdAt = createdAt
                    )
                )
            }
        }

        view?.updateTopicList(model = topicListCellModel)

    }

    private fun requestUserAvatar(avatarTemplate: String, into: ImageView) {
        Glide.with(context)
            .load(
                "https://${BuildConfig.DiscourseDomain}${avatarTemplate.replace(
                    "{size}",
                    "64"
                )}"
            )
            .placeholder(R.drawable.user_avatar)
            .centerCrop()
            .into(into)
    }

    // MARK: - UserDetailFragmentDelegate protocol implementation
    override fun requestLoggedUser() {
        val job = async {
            localRepository.getLoggedUser()
        }

        var id: Int
        var username: String
        var name: String?
        var avatarTemplate: String?
        var lastSeenAt: String? = ""
        var moderator: Boolean?
        var logged: Boolean

        launch(Dispatchers.Main) {
            try {
                val response = job.await()
//            view?.enableLoading(false)
                if (response != null) {

                    id = response.id
                    username = response.username
                    name = response.name
                    avatarTemplate = response.avatarTemplate
                    response.lastSeenAt?.let {

                        val timeOffset = TimeOffsetRepository.getTimeOffset(it)
                        val quantityString = when (timeOffset.unit) {
                            Calendar.YEAR -> R.plurals.years
                            Calendar.MONTH -> R.plurals.months
                            Calendar.DAY_OF_MONTH -> R.plurals.days
                            Calendar.HOUR -> R.plurals.hours
                            Calendar.MINUTE -> R.plurals.minutes
                            else -> R.plurals.years
                        }

                        lastSeenAt = if (timeOffset.amount != 0) {
                            context.resources.getQuantityString(
                                quantityString,
                                timeOffset.amount,
                                timeOffset.amount
                            )
                        } else {
                            context.resources.getString(R.string.minutes_zero)
                        }

                    }

                    moderator = response.moderator
                    logged = response.logged

                    userDetailModel = UserDetailModel(
                        id = id,
                        username = username,
                        name = name,
                        avatarTemplate = avatarTemplate,
                        lastSeenAt = lastSeenAt,
                        moderator = moderator,
                        logged = logged
                    )

                    requestPrivateMessagesByUser(username = username)

                    view?.updateUserDetailModel(userDetailModel = userDetailModel)

                } else {
                    print("No logged user found")
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

    override fun onLogOutClicked() {
        localRepository.logOutUser(userDetailModel = userDetailModel)
        view?.launchLoginActivity()
    }

    // MARK: - TopicListFragmentDelegate protocol implementation
    override fun onTopicSelected(topic: TopicCellModel) {
        view?.navigateToTopicDetail(topicId = topic.id)
    }

    override fun onTopicListRefreshListener(topicFilterName: TopicFilterType) {
        pageNumber = 0
        topicListCellModel.clear()

        when (topicFilterName) {
            TopicFilterType.Latest -> fetchLatestTopicsAndHandleResponse()
            TopicFilterType.Top -> fetchTopTopicsAndHandleResponse()
        }
    }

    override fun requestTopics(topicFilterName: TopicFilterType) {
        when (topicFilterName) {
            TopicFilterType.Latest -> fetchLatestTopicsAndHandleResponse()
            TopicFilterType.Top -> fetchTopTopicsAndHandleResponse()
        }
    }

    // MARK: - TopicFilterNavigation
    override fun onNavigationLatestTopicsItemSelected() {
        requestTopics(topicFilterName = TopicFilterType.Latest)
    }

    override fun onNavigationTopTopicsItemSelected() {
        requestTopics(topicFilterName = TopicFilterType.Top)
    }

}

interface MainActivityModelViewProtocol {
    fun updateUserDetailModel(userDetailModel: UserDetailModel)
    fun launchLoginActivity()
    fun updatePrivateMessages(count: Int)
    fun showError(message: String)
    fun updateTopicList(model: List<TopicCellModel>)
    fun navigateToTopicDetail(topicId: Int)
}
