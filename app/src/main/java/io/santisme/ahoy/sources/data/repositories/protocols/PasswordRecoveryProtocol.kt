package io.santisme.ahoy.sources.data.repositories.protocols

import io.santisme.ahoy.domain.models.local.PasswordRecoveryModel
import io.santisme.ahoy.domain.responses.PasswordRecoveryResponse
import retrofit2.Response

interface PasswordRecoveryProtocol {
    suspend fun recoverPassword(passwordRecoveryModel: PasswordRecoveryModel): Response<PasswordRecoveryResponse>
}