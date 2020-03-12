package io.santisme.ahoy.sources.main

import android.content.Context
import io.santisme.ahoy.domain.models.UserModel
import io.santisme.ahoy.domain.models.UserModelWrapper
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import kotlinx.coroutines.*
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

    lateinit var userModelWrapper: UserModelWrapper

    override fun requestLoggedUser() {
        val job = async {
            localRepository.getLoggedUser()
        }

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

                username = response.username
                name = response.name
                avatarTemplate = response.avatarTemplate
                lastSeenAt = response.lastSeenAt
                moderator = response.moderator
                logged = response.logged

                view?.updateUserModel(
                    UserModel(
                        id = 0,
                        username = username,
                        name = name,
                        avatarTemplate = avatarTemplate,
                        lastSeenAt = lastSeenAt,
                        moderator = moderator,
                        logged = logged
                    )
                )

            } else {
                print("No logged user found")
            }
        }

    }

}

interface MainActivityModelViewProtocol {
    fun updateUserModel(userModel: UserModel)
}
