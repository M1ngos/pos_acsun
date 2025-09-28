package com.acsun.pos.presentation.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acsun.pos.presentation.navigation.AuthNavigation
import com.acsun.pos.presentation.ui.splash.SplashScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
internal fun AuthNavigation(
    authViewModel: AuthViewModel = koinInject(),
    navigateToMain: () -> Unit,
    onToggleFullscreen: ((Boolean) -> Unit)?,
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        delay(4000)
        when (val state = authState) {
            is AuthViewModel.AuthState.Success -> {
                if (state.isLoggedIn) {
//                    onToggleFullscreen?.invoke(true)
                    navigateToMain()
                } else {
                    navController.navigate(AuthNavigation.Login) {
                        popUpTo(AuthNavigation.Splash) { inclusive = true }
                    }
                }
            }
            is AuthViewModel.AuthState.Error -> {
                // TODO: Show error message
                navController.navigate(AuthNavigation.Login) {
                    popUpTo(AuthNavigation.Splash) { inclusive = true }
                }
            }
            AuthViewModel.AuthState.Idle -> {
                authViewModel.checkUserLoggedIn()
            }
            AuthViewModel.AuthState.Loading -> {
                // SplashScreen is shown by default
            }
        }
    }

    NavHost(
        startDestination = AuthNavigation.Splash,
        navController = navController,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<AuthNavigation.Splash> {
            SplashScreen()
        }

        composable<AuthNavigation.Login> {
            LoginScreen(
                authViewModel = authViewModel,
                onSignIn = { username, password ->
                    authViewModel.login(username, password)
                },
                navigateToRegister = {
                    navController.navigate(AuthNavigation.Register)
                }
            )
        }

        composable<AuthNavigation.Register> {
            RegisterScreen(
                authViewModel = authViewModel,
                onSignUp = { username, password ->
                    authViewModel.register(username, password)
                },
                onExisting = {
                    navController.navigate(AuthNavigation.Login)
                }
            )
        }
    }
}

