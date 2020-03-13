package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.models.local.SignInModel
import io.santisme.ahoy.domain.responses.SignInResponse
import retrofit2.Response

interface SignInRepositoryProtocol {
    suspend fun signIn(signInModel: SignInModel): Response<SignInResponse>
}