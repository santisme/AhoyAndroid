package io.santisme.ahoy.domain.models.local

import org.json.JSONObject

data class SignUpModel(val name: String, val username: String, val email: String, val password: String) {
    fun toJson(): JSONObject {
        return JSONObject().put("name", name).put("username", username).put("email", email)
            .put("password", password).put("active", true).put("approved", true)
    }
}

data class SignUpModelWrapper(val signUpModel: SignUpModel, val repeatedPassword: String)