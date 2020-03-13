package io.santisme.ahoy.domain.models.local

import org.json.JSONObject

data class PasswordRecoveryModel(val login: String) {
    fun toJson(): JSONObject {
        return JSONObject().put("login", login)
    }
}