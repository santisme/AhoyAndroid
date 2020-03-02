package io.santisme.ahoy.sources.data.repositories.local

import android.content.Context
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.domain.models.PasswordRecoveryModel
import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.domain.models.SignUpModel
import io.santisme.ahoy.domain.requests.PasswordRecoveryRequest
import io.santisme.ahoy.domain.requests.SignInRequest
import io.santisme.ahoy.domain.requests.SignUpRequest
import io.santisme.ahoy.domain.responses.PasswordRecoveryResponse
import io.santisme.ahoy.domain.responses.SignUpResponse
import io.santisme.ahoy.sources.data.repositories.protocols.PasswordRecoveryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignInRepositoryProtocol
import io.santisme.ahoy.sources.data.repositories.protocols.SignUpRepositoryProtocol
import io.santisme.ahoy.sources.networking.APIProvider
import retrofit2.Response

object LocalRepository : SignInRepositoryProtocol, SignUpRepositoryProtocol,
    PasswordRecoveryProtocol {

    override suspend fun signIn(signInModel: SignInModel): Response<SignInModel> {
        return APIProvider.retrofit.create(SignInRequest::class.java).signIn(
            username = signInModel.username,
            headerUsername = signInModel.username
        )
    }

    override suspend fun signUp(signUpModel: SignUpModel): Response<SignUpResponse> {
        return APIProvider.retrofit.create(SignUpRequest::class.java).signUp(request = signUpModel, headerUsername = BuildConfig.DiscourseAdmin)
    }

    override suspend fun recoverPassword(passwordRecoveryModel: PasswordRecoveryModel): Response<PasswordRecoveryResponse> {
        return APIProvider.retrofit.create(PasswordRecoveryRequest::class.java).recoverPassword(request = passwordRecoveryModel, headerUsername = BuildConfig.DiscourseAdmin)
    }

}
