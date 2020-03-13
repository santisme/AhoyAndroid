package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import android.util.Log
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.domain.database.TopicEntity
import io.santisme.ahoy.domain.models.api.TopicModel
import io.santisme.ahoy.domain.models.api.UserModel
import io.santisme.ahoy.domain.models.local.PasswordRecoveryModel
import io.santisme.ahoy.domain.models.local.SignInModel
import io.santisme.ahoy.domain.models.local.SignUpModel
import io.santisme.ahoy.domain.requests.PasswordRecoveryRequest
import io.santisme.ahoy.domain.requests.PrivateMessagesRequestByUser
import io.santisme.ahoy.domain.requests.SignInRequest
import io.santisme.ahoy.domain.requests.SignUpRequest
import io.santisme.ahoy.domain.responses.*
import io.santisme.ahoy.sources.data.repositories.protocols.PasswordRecoveryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.PrivateMessagesProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignInRepositoryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignUpRepositoryProtocol
import io.santisme.ahoy.sources.networking.APIProvider
import retrofit2.Response

class LocalRepository(private val context: Context) : SignInRepositoryProtocol,
    SignUpRepositoryProtocol,
    PasswordRecoveryProtocol, DatabaseRepositoryProtocol, PrivateMessagesProtocol {

    val database: LocalRepositoryProtocol = DatabaseRepository(context = context)

    override suspend fun signIn(signInModel: SignInModel): Response<SignInResponse> {
        try {
            return APIProvider.retrofit.create(SignInRequest::class.java).signIn(
                username = signInModel.username,
                headerUsername = signInModel.username
            )
        } catch (error: Exception) {
            Log.e("SIGN_IN", "Error SignIn on remote API")
            with(error) {
                printStackTrace()
                throw this
            }
        }

    }

    override suspend fun signUp(signUpModel: SignUpModel): Response<SignUpResponse> {
        try {
            return APIProvider.retrofit.create(SignUpRequest::class.java)
                .signUp(request = signUpModel, headerUsername = BuildConfig.DiscourseAdmin)
        } catch (error: Exception) {
            Log.e("SIGN_UP", "Error fetching Private messages from remote API")
            with(error) {
                printStackTrace()
                throw this
            }
        }
    }

    override suspend fun recoverPassword(passwordRecoveryModel: PasswordRecoveryModel): Response<PasswordRecoveryResponse> {
        try {
            return APIProvider.retrofit.create(PasswordRecoveryRequest::class.java).recoverPassword(
                request = passwordRecoveryModel,
                headerUsername = BuildConfig.DiscourseAdmin
            )
        } catch (error: Exception) {
            Log.e("PRIVATE_MESSAGES", "Error fetching Private messages from remote API")
            with(error) {
                printStackTrace()
                throw this
            }
        }
    }

    // MARK: - Protocol PrivateMessagesProtocol implementation
    override suspend fun requestPrivateMessagesByUser(username: String): Response<PrivateMessagesByUserResponse> {
        try {
            return APIProvider.retrofit.create(PrivateMessagesRequestByUser::class.java)
                .privateMessagesByUser(
                    username = username,
                    headerUsername = BuildConfig.DiscourseAdmin
                )
        } catch (error: Exception) {
            Log.e("PRIVATE_MESSAGES", "Error fetching Private messages from remote API")
            with(error) {
                printStackTrace()
                throw this
            }
        }

    }

    override fun savePrivateMessages(topicList: List<TopicModel>) {
        try {
            database.insertPrivateMessages(topicList = topicList)
        } catch (error: Exception) {
            Log.e("PRIVATE_MESSAGES", "Error inserting Private messages to database")
            with(error) {
                printStackTrace()
                throw this
            }
        }
    }

    // MARK: - Protocol DatabaseRepositoryProtocol implementation
    // MARK: - Save Methods
    override fun saveUserList(userList: List<UserModel>) {
        database.insertAllUsers(userList = userList)
    }

    override fun saveLoggedUser(user: UserModel) {
        val loggedUser = with(user) {
            UserModel(
                id = id,
                username = username,
                name = name,
                avatarTemplate = avatarTemplate,
                lastSeenAt = lastSeenAt,
                moderator = moderator,
                logged = true
            )

        }
        database.insertAllUsers(userList = listOf(loggedUser))
    }

    override fun saveUser(user: UserModel) {
        database.insertAllUsers(userList = listOf(user))
    }

    // MARK: - Delete Methods
    override fun deleteUser(user: UserModel) {
        database.deleteUser(userModel = user)
    }

    // MARK: - Update Methods
    override fun updateUser(user: UserModel) {
        database.updateUser(userModel = user)
    }

    suspend fun getLoggedUser(): UserModel? {
        return database.fetchLoggedUser()
    }

    fun logOutUser(userModel: UserModel) {
        val loggedOutUserModel = with(userModel) {
            UserModel(
                id = id,
                username = username,
                name = name,
                avatarTemplate = avatarTemplate,
                lastSeenAt = lastSeenAt,
                moderator = moderator,
                logged = false
            )
        }
        updateUser(user = loggedOutUserModel)
    }

}

interface LocalRepositoryProtocol {
    fun insertAllUsers(userList: List<UserModel>)
    fun deleteUser(userModel: UserModel)
    fun updateUser(userModel: UserModel)
    suspend fun fetchLoggedUser(): UserModel?
    fun insertPrivateMessages(topicList: List<TopicModel>)
}