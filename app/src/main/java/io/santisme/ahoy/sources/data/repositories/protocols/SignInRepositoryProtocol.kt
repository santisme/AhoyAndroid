package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.models.SignInModel
import retrofit2.Response

interface SignInRepositoryProtocol {
    suspend fun signIn(signInModel: SignInModel): Response<SignInModel>
}