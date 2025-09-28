package com.acsun.pos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberNotification
import androidx.compose.ui.window.rememberTrayState
import com.acsun.pos.presentation.ui.auth.AuthViewModel
import kotlinx.coroutines.delay

fun main() = application {
    val windowState = rememberWindowState(
        width = 400.dp,
        height = 700.dp,
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    val trayState = rememberTrayState()
    val notification =
        rememberNotification("Captação de dados - INATRO ", "Bem-vindo ao sistema!")
    val trayIconPainter = BitmapPainter(
        useResource("drawables/tray_icon_placeholder_remove.png", ::loadImageBitmap)
    )

    Tray(
        state = trayState,
        icon = trayIconPainter,
        menu = {
            Item("Enviar Notificação", onClick = {
                trayState.sendNotification(notification)
            })
            Item("Sair", onClick = {
                exitApplication()
            })
        }
    )

    LaunchedEffect(Unit) {
        delay(500) // Wait for the tray to initialize
        trayState.sendNotification(notification)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Acgest",
        state = windowState,
        undecorated = true,
        resizable = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            App(
                context = null,
                onToggleFullscreen = { fullscreen ->
                    windowState.placement = if (fullscreen)
                        WindowPlacement.Fullscreen
                    else
                        WindowPlacement.Floating
                }
            )
        }
    }
}