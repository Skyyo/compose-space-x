package com.skyyo.samples.features.verticalPagerWithFling.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import com.skyyo.samples.features.exoPlayer.common.VideoItem

@Composable
fun AutoPlayVideoCard(
    modifier: Modifier = Modifier,
    videoItem: VideoItem,
    isPlaying: Boolean,
    isFirstFrameRendered: Boolean,
    exoPlayer: ExoPlayer,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FullscreenVideoPlayer(if (isPlaying) exoPlayer else null)
        if (!isFirstFrameRendered) {
            FullscreenVideoThumbnail(videoItem.thumbnailFilePath)
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${videoItem.id}",
            fontSize = 42.sp,
            color = Color.Red,
            fontWeight = FontWeight.W700
        )
    }
}
