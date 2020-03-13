package io.santisme.ahoy.sources.main

import android.util.Log
import io.santisme.ahoy.domain.models.api.UserModel
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class MainActivityModelView(
    private val view: MainActivityModelViewProtocol?,
//    private val context: Context,
    private val localRepository: LocalRepository
) :
    MainActivityDelegate, CoroutineScope {
    private val job: CompletableJob = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    lateinit var userModel: UserModel

    override fun requestLoggedUser() {
        val job = async {
            localRepository.getLoggedUser()
        }

        var id: Int
        var username: String
        var name: String?
        var avatarTemplate: String?
        var lastSeenAt: String?
        var moderator: Boolean?
        var logged: Boolean

        launch(Dispatchers.Main) {
            val response = job.await()

//            view?.enableLoading(false)
            if (response != null) {

                id = response.id
                username = response.username
                name = response.name
                avatarTemplate = response.avatarTemplate
                lastSeenAt = response.lastSeenAt
                moderator = response.moderator
                logged = response.logged

                userModel = UserModel(
                    id = id,
                    username = username,
                    name = name,
                    avatarTemplate = avatarTemplate,
                    lastSeenAt = lastSeenAt,
                    moderator = moderator,
                    logged = logged
                )

                requestPrivateMessagesByUser(username = username)

                view?.updateUserModel(userModel = userModel)

            } else {
                print("No logged user found")
            }
        }

    }

    override fun onLogOutClicked() {
        localRepository.logOutUser(userModel = userModel)
        view?.launchLoginActivity()
    }

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

}

interface MainActivityModelViewProtocol {
    fun updateUserModel(userModel: UserModel)
    fun launchLoginActivity()
    fun updatePrivateMessages(count: Int)
    fun showError(message: String)
}
