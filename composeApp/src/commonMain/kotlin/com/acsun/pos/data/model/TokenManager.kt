package com.acsun.pos.data.model

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class TokenManager(private val settings: Settings) {

    companion object {
        private const val TOKEN_KEY = "jwt_token"
    }

    fun saveToken(token: String) {
        settings[TOKEN_KEY] = token
    }

    fun getToken(): String? {
        return settings[TOKEN_KEY]
    }

    fun clearToken() {
        settings.remove(TOKEN_KEY)
    }

    fun isTokenValid(): Boolean {
        val token = getToken()
        // In a real scenario, you would decode the token and check its expiration date.
        // For this mock implementation, we'll just check if the token is not null and not empty.
        return !token.isNullOrBlank()
    }
}
