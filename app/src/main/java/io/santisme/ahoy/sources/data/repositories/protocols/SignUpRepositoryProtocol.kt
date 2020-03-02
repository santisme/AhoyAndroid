package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.models.SignUpModel
import io.santisme.ahoy.domain.responses.SignUpResponse
import retrofit2.Response

interface SignUpRepositoryProtocol {
    suspend fun signUp(signUpModel: SignUpModel): Response<SignUpResponse>
}