package io.santisme.ahoy.domain.models

import org.json.JSONObject

data class SignUpModel(val username: String, val email: String, val password: String) {
    fun toJson(): JSONObject {
        return JSONObject().put("name", username).put("username", username).put("email", email)
            .put("password", password).put("active", true).put("approved", true)
    }
}

data class SignUpModelWrapper(val signUpModel: SignUpModel, val confirmPassword: String)