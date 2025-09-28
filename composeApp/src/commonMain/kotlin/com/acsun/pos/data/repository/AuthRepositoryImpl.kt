package com.acsun.pos.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)

@Serializable
data class RegisterRequest(val username: String, val password: String)

@Serializable
data class ErrorResponse(val message: String)

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun login(username: String, password: String): Result<String> {
        return try {
            // TODO: Replace with your actual API endpoint
            val response = httpClient.post("http://127.0.0.1:8000/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
            if (response.status == HttpStatusCode.OK) {
                val token = response.body<LoginResponse>().token
                Result.success(token)
            } else {
                val errorBody = response.bodyAsText()
                val errorMessage = try {
                    Json.decodeFromString<ErrorResponse>(errorBody).message
                } catch (e: Exception) {
                    "Login failed with status ${response.status.value}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(username: String, password: String): Result<Unit> {
        return try {
            // TODO: Replace with your actual API endpoint
            val response = httpClient.post("http://127.0.0.1:8000/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(username, password))
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorMessage = try {
                    Json.decodeFromString<ErrorResponse>(errorBody).message
                } catch (e: Exception) {
                    "Registration failed with status ${response.status.value}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun validateToken(token: String): Result<Boolean> {
        return try {
            // TODO: Replace with your actual API endpoint
            val response = httpClient.get("http://127.0.0.1:8000/auth/validate-token") {
                bearerAuth(token)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(true)
            } else {
                Result.success(false) // Token is not valid or expired
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
