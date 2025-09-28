package com.acsun.pos.presentation.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pos_acsun.composeapp.generated.resources.Res
import pos_acsun.composeapp.generated.resources.app_logo
import pos_acsun.composeapp.generated.resources.app_name
import pos_acsun.composeapp.generated.resources.app_slogan

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acsun.pos.presentation.navigation.AuthNavigation

@Composable
internal fun SplashScreen() {
    val scope = rememberCoroutineScope()

    val logoOffset = remember { Animatable(-200f) }
    val titleOffset = remember { Animatable(-100f) }
    val titleAlpha = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "loaderPulse")
    val loaderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "loaderAlpha"
    )
    var sloganText by remember { mutableStateOf("") }
    val fullSlogan = stringResource(Res.string.app_slogan)

    LaunchedEffect(Unit) {
        logoOffset.animateTo(0f, tween(800, easing = LinearOutSlowInEasing))
        delay(200)
        // Animate both offset and alpha for title
        launch {
            titleOffset.animateTo(0f, tween(800, easing = LinearOutSlowInEasing))
        }
        launch {
            titleAlpha.animateTo(1f, tween(800, easing = LinearOutSlowInEasing))
        }
        delay(200)
        alpha.animateTo(1f, tween(500, easing = LinearOutSlowInEasing))
        fullSlogan.forEachIndexed { index, c ->
            sloganText = fullSlogan.take(index + 1)
            delay(50)
        }
        delay(1000)
//        navController.navigate(AuthNavigation.Login)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = logoOffset.value.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(y = titleOffset.value.dp)
                    .alpha(titleAlpha.value)
            )

            Text(
                text = sloganText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(48.dp))

            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.alpha(loaderAlpha)
            )
        }
    }
}

@Preview
@Composable
internal fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen()
}


