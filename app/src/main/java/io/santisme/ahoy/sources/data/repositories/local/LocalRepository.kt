package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.domain.models.PasswordRecoveryModel
import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.domain.models.SignUpModel
import io.santisme.ahoy.domain.models.UserModel
import io.santisme.ahoy.domain.requests.PasswordRecoveryRequest
import io.santisme.ahoy.domain.requests.SignInRequest
import io.santisme.ahoy.domain.requests.SignUpRequest
import io.santisme.ahoy.domain.responses.PasswordRecoveryResponse
import io.santisme.ahoy.domain.responses.SignInResponse
import io.santisme.ahoy.domain.responses.SignUpResponse
import io.santisme.ahoy.sources.data.repositories.protocols.PasswordRecoveryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignInRepositoryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignUpRepositoryProtocol
import io.santisme.ahoy.sources.networking.APIProvider
import retrofit2.Response

class LocalRepository(private val context: Context) : SignInRepositoryProtocol,
    SignUpRepositoryProtocol,
    PasswordRecoveryProtocol, DatabaseRepositoryProtocol {

    val database: LocalRepositoryProtocol = DatabaseRepository(context = context)

    override suspend fun signIn(signInModel: SignInModel): Response<SignInResponse> {
        return APIProvider.retrofit.create(SignInRequest::class.java).signIn(
            username = signInModel.username,
            headerUsername = signInModel.username
        )
    }

    override suspend fun signUp(signUpModel: SignUpModel): Response<SignUpResponse> {
        return APIProvider.retrofit.create(SignUpRequest::class.java)
            .signUp(request = signUpModel, headerUsername = BuildConfig.DiscourseAdmin)
    }

    override suspend fun recoverPassword(passwordRecoveryModel: PasswordRecoveryModel): Response<PasswordRecoveryResponse> {
        return APIProvider.retrofit.create(PasswordRecoveryRequest::class.java).recoverPassword(
            request = passwordRecoveryModel,
            headerUsername = BuildConfig.DiscourseAdmin
        )
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
        database.deleteUser(user = user)
    }

    // MARK: - Update Methods
    override fun updateUser(user: UserModel) {
        database.updateUser(user = user)
    }

    suspend fun getLoggedUser(): UserModel? {
        return database.fetchLoggedUser()
    }
}

interface LocalRepositoryProtocol {
    fun insertAllUsers(userList: List<UserModel>)
    fun deleteUser(user: UserModel)
    fun updateUser(user: UserModel)
    suspend fun fetchLoggedUser(): UserModel?
}