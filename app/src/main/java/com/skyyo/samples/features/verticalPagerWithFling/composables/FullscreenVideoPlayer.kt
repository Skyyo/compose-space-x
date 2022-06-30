package com.skyyo.samples.features.verticalPagerWithFling.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@Composable
fun FullscreenVideoPlayer(exoPlayer: ExoPlayer?) {
    val context = LocalContext.current
    val playerView = remember {
        PlayerView(context).apply {
            setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }
    }

    DisposableEffect(exoPlayer) {
        playerView.player = exoPlayer
        onDispose { playerView.player = null }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier.fillMaxSize()
    )
}
