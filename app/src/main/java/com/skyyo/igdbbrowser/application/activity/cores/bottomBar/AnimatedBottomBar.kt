package com.skyyo.igdbbrowser.application.activity.cores.bottomBar

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.skyyo.igdbbrowser.application.Screens
import com.skyyo.igdbbrowser.application.activity.COLLAPSE_ANIMATION_DURATION
import com.skyyo.igdbbrowser.application.activity.EXPAND_ANIMATION_DURATION
import com.skyyo.igdbbrowser.application.activity.FADE_IN_ANIMATION_DURATION
import com.skyyo.igdbbrowser.application.activity.FADE_OUT_ANIMATION_DURATION
import com.skyyo.igdbbrowser.theme.DarkGray
import com.skyyo.igdbbrowser.theme.Purple500

@ExperimentalAnimationApi
@Composable
fun AnimatedBottomBar(
    items: List<Screens>,
    selectedIndex: Int,
    isBottomBarVisible: Boolean,
    onTabClick: (index: Int, route: String) -> Unit
) {
    val density = LocalDensity.current
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val enterSlide = remember {
        slideIn(
            animationSpec = tween(EXPAND_ANIMATION_DURATION),
            initialOffset = { with(density) { IntOffset(0, 56.dp.roundToPx()) } })
    }
    val exitSlide = remember {
        slideOut(
            animationSpec = tween(COLLAPSE_ANIMATION_DURATION),
            targetOffset = { with(density) { IntOffset(0, 56.dp.roundToPx()) } })
    }

    return AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = enterSlide + enterFadeIn,
        exit = exitSlide + exitFadeOut,
//        modifier = Modifier.navigationBarsPadding()
    ) {
        Column {
            BottomNavigation(backgroundColor = DarkGray) {
                items.forEachIndexed { index, screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = index == selectedIndex,
                        onClick = { onTabClick(index, screen.route) }
                    )
                }
            }
            Spacer(
                Modifier
                    .background(DarkGray)
                    .navigationBarsHeight()
                    .fillMaxWidth()
            )
        }
    }
}