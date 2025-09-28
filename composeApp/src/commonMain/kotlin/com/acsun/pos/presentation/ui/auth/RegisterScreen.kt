package com.acsun.pos.presentation.ui.auth

import androidx.compose.runtime.Composable

//@Composable
//expect fun RegisterScreen(
//    email: String,
//    onEmailChange: (String) -> Unit,
//    password: String,
//    onPasswordChange: (String) -> Unit,
//    onSignUp: () -> Unit,
//    onExisting: () -> Unit
//)

@Composable
expect fun RegisterScreen(
    authViewModel: AuthViewModel,
    onSignUp: (String, String) -> Unit,
    onExisting: () -> Unit
)