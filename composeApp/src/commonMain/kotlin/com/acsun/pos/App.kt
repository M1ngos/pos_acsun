package com.acsun.pos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acsun.pos.di.appModule
import com.acsun.pos.di.common.Context
import com.acsun.pos.presentation.SharedViewModel
import com.acsun.pos.presentation.navigation.AppNavigation
import com.acsun.pos.presentation.theme.AppTheme
import com.acsun.pos.presentation.ui.auth.AuthNavigation
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App(context: Context?, onToggleFullscreen: ((Boolean) -> Unit)? = null) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        AppTheme {

            val navController = rememberNavController()
            val viewModel: SharedViewModel = koinInject()

//            LaunchedEffect(key1 = viewModel.tokenManager.state.value.isTokenAvailable) {
//                if (!viewModel.tokenManager.state.value.isTokenAvailable) {
//                    navController.popBackStack()
//                    navController.navigate(AppNavigation.Splash)
//                }
//            }
            
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = AppNavigation.Auth,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable<AppNavigation.Auth> {
                        AuthNavigation(
                            onToggleFullscreen = onToggleFullscreen,
                            navigateToMain = {
                                navController.popBackStack()
                                navController.navigate(AppNavigation.Main)
                            }
                        )
                    }

                    composable<AppNavigation.Main> {
                        Text("Yay here in!")
                    }
                }
            }
        }
    }
}