package com.acsun.pos.presentation.ui.auth

import com.acsun.pos.data.model.TokenManager
import com.acsun.pos.data.repository.AuthRepository
import com.acsun.pos.presentation.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed interface AuthState {
        data object Idle : AuthState
        data object Loading : AuthState
        data class Success(val isLoggedIn: Boolean) : AuthState
        data class Error(val message: String) : AuthState
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    init {
        checkUserLoggedIn()
    }

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val token = tokenManager.getToken()
                val isLoggedIn = if (token != null) {
                    authRepository.validateToken(token)
                        .onSuccess { isValid ->
                            if (!isValid) tokenManager.clearToken() // Clear invalid token
                        }
                        .getOrDefault(false)
                } else {
                    false
                }
                _authState.value = AuthState.Success(isLoggedIn)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Failed to check login status: ${e.message}")
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.login(username, password)
                .onSuccess {
                    tokenManager.saveToken(it)
                    _authState.value = AuthState.Success(true)
                }
                .onFailure {
                    _authState.value = AuthState.Error(it.message ?: "Login failed")
                }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.register(username, password)
                .onSuccess {
                    _authState.value = AuthState.Success(false) // Registration successful, but not logged in
                }
                .onFailure {
                    _authState.value = AuthState.Error(it.message ?: "Registration failed")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            _authState.value = AuthState.Success(false)
        }
    }
}
