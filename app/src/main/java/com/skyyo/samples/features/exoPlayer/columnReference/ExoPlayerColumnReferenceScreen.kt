package com.skyyo.samples.features.exoPlayer.columnReference

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.skyyo.samples.features.exoPlayer.common.VideoItem

@Composable
fun ExoPlayerColumnReferenceScreen(viewModel: ExoPlayerColumnReferenceViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val listState = rememberLazyListState()
    val videos by viewModel.videos.collectAsState()
    val playingVideoItem by viewModel.currentlyPlayingItem.collectAsState()
    var isPlayingItemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow {
            listState.visibleAreaContainsItem(playingVideoItem, videos)
        }.collect { isVisible ->
            isPlayingItemVisible = isVisible
        }
    }

    LaunchedEffect(playingVideoItem) {
        if (playingVideoItem == null) {
            exoPlayer.pause()
        } else {
            exoPlayer.setMediaItem(
                MediaItem.fromUri(playingVideoItem!!.mediaUrl),
                playingVideoItem!!.lastPlayedPosition
            )
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    LaunchedEffect(isPlayingItemVisible) {
        if (!isPlayingItemVisible && playingVideoItem != null) {
            viewModel.onPlayVideoClick(exoPlayer.currentPosition, playingVideoItem)
        }
    }

    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (playingVideoItem == null) return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.systemBars
            .only(WindowInsetsSides.Vertical)
            .add(WindowInsets(left = 16.dp, right = 16.dp, bottom = 8.dp))
            .asPaddingValues()
    ) {
        items(videos, VideoItem::id) { video ->
            Spacer(modifier = Modifier.height(16.dp))
            VideoCardReference(
                videoItem = video,
                exoPlayer = exoPlayer,
                isPlaying = video.id == playingVideoItem?.id,
                onClick = {
                    viewModel.onPlayVideoClick(exoPlayer.currentPosition, video)
                }
            )
        }
    }
}

private fun LazyListState.visibleAreaContainsItem(
    currentlyPlayedVideoItem: VideoItem?,
    videos: List<VideoItem>
) = when {
    currentlyPlayedVideoItem == null -> false
    videos.isEmpty() -> false
    else -> {
        layoutInfo.visibleItemsInfo.map { videos[it.index] }.contains(currentlyPlayedVideoItem)
    }
}
