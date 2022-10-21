package com.skyyo.samples.features.sampleContainer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skyyo.samples.extensions.FixInAppLanguageSwitchLayoutDirection
import com.skyyo.samples.features.textGradient.composables.RunningGradientText

@Composable
fun SampleContainerScreen(viewModel: SampleContainerViewModel = hiltViewModel()) = FixInAppLanguageSwitchLayoutDirection {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Pagination(viewModel)
        BottomSheets(viewModel)
        InputValidations(viewModel)
        Camera(viewModel)
        Maps(viewModel)
        ExoPlayerSamples(viewModel)
        ThemeAndLocalization(viewModel)
        ScrollBasedAnimations(viewModel)
        UIelements(viewModel)
        NavigateWithResults(viewModel)
        NavigationCores(viewModel)
        Text(text = "uncategorized")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::goAutoScroll
        ) { Text(text = "auto scroll") }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goSnackbar) {
            Text(text = "snackbar")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goDominantColor) {
            Text(text = "dominant color")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goSnap) {
            Text(text = "snapping")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goZoomable) {
            Text(text = "zoomable")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goPdfViewer) {
            Text(text = "pdf viewer")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goHealthConnect) {
            Text(text = "health connect")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goImagePicker) {
            Text(text = "image picker without permissions")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goLanguagePicker) {
            Text(text = "language picker")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goCardRecognition) {
            Text(text = "card recognition")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goImageTextRecognition) {
            Text(text = "static image text recognition")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goCameraTextRecognition) {
            Text(text = "camera text recognition")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goGooglePay) {
            Text(text = "google pay")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goInteractionTracking) {
            Text(text = "user interaction tracking result")
        }
    }
}

@Composable
fun NavigationCores(viewModel: SampleContainerViewModel) {
    Text(text = "navigation cores")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goBottomBar
    ) { Text(text = "bottom bar") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goNavigationDrawer
    ) { Text(text = "navigation drawer") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun NavigateWithResults(viewModel: SampleContainerViewModel) {
    Text(text = "Share data between composables")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goNavigationWithValuesSimple
    ) { Text(text = "navigate forward/back (primitives)") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goNavigationWithValuesObject
    ) { Text(text = "navigate forward/back (parcelable)") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goHiltComposeSharedViewModel
    ) { Text(text = "hilt + shared viewModel") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun UIelements(viewModel: SampleContainerViewModel) {
    Text(text = "UI elements")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goOtp
    ) { Text(text = "otp view") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goViewPager
    ) { Text(text = "view pager") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goInfiniteViewPager
    ) { Text(text = "infinite view pager") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goStickyHeaders
    ) { Text(text = "sticky headers") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goTable
    ) { Text(text = "table") }
    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goCustomView) {
        Text(
            text = "custom view",
            style = LocalTextStyle.current.copy(
                shadow = Shadow(
                    color = Color.Red,
                    offset = Offset(x = 4f, y = 4f),
                    blurRadius = 8f
                )
            )
        )
    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goMarqueeText
    ) { Text(text = "marquee text") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goTextSpans
    ) { Text(text = "text spans") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goTextGradient
    ) {
        val gradientNeonColors = remember {
            listOf(
                Color(0xFF000272),
                Color(0xFF341677),
                Color(0xFFA32F80),
                Color(0xFFFF6363),
            )
        }
        RunningGradientText(text = "text gradient", colors = gradientNeonColors)
    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goAutofill
    ) { Text(text = "autofill") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goImeAwareLazyColumn
    ) { Text(text = "ime aware lazy column") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goBlur
    ) { Text(text = "blur") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ScrollBasedAnimations(viewModel: SampleContainerViewModel) {
    Text(text = "Scroll based animations")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goNestedHorizontalLists
    ) { Text(text = "app bar auto-elevation animation") }

    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goParallaxEffect) {
        Text(text = "parallax effect ")
    }
    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goScrollAnimation1) {
        Text(text = "scroll animation 1")
    }
    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goGradientScroll) {
        Text(text = "gradient change")
    }
    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goNoticeableScrollableRow) {
        Text(text = "noticeable scrollable row")
    }
    Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::goDragAndDrop) {
        Text(text = "drag and drop")
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ThemeAndLocalization(viewModel: SampleContainerViewModel) {
    Text(text = "Theme & localization")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goForceTheme
    ) { Text(text = "force theme") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Maps(viewModel: SampleContainerViewModel) {
    Text(text = "Maps")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goMap
    ) { Text(text = "google map") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Camera(viewModel: SampleContainerViewModel) {
    Text(text = "Camera")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goCameraX
    ) { Text(text = "camera x") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goQrScanning
    ) { Text(text = "scan qr code with ML kit") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goQrScanningWithoutPermissions
    ) { Text(text = "scan qr code without permissions") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ExoPlayerSamples(viewModel: SampleContainerViewModel) {
    Text(text = "ExoPlayer in LazyColumn")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goExoPlayerColumnReference
    ) { Text(text = "reference based") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goExoPlayerColumnIndexed
    ) { Text(text = "index based") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goExoPlayerColumnAutoplay
    ) { Text(text = "auto-playback") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goExoPlayerColumnDynamicThumb
    ) { Text(text = "dynamic thumbnails") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goVerticalPagerWithFling
    ) { Text(text = "vertical pager with fling") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun InputValidations(viewModel: SampleContainerViewModel) {
    Text(text = "Input validations")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goInputManualValidation
    ) { Text(text = "manual") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goInputAutoValidation
    ) { Text(text = "auto") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goInputDebounceValidation
    ) { Text(text = "debounce") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun BottomSheets(viewModel: SampleContainerViewModel) {
    Text(text = "Bottom sheets")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goBottomSheetDestination
    ) { Text(text = "as destination") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goBottomSheetsContainer
    ) { Text(text = "modal") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goBottomSheetsScaffold
    ) { Text(text = "persistent") }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Pagination(viewModel: SampleContainerViewModel) {
    Text(text = "Pagination")
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goPaginationSimple
    ) { Text(text = "simple") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goPaginationRoom
    ) { Text(text = "simple + room") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goPaginationPaging
    ) { Text(text = "paging") }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::goPaginationPagingRoom
    ) { Text(text = "paging + room") }
    Spacer(modifier = Modifier.height(16.dp))
}
