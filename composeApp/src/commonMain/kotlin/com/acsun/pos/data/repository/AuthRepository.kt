package com.acsun.pos.data.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<String>
    suspend fun register(username: String, password: String): Result<Unit>
    suspend fun validateToken(token: String): Result<Boolean>
}
