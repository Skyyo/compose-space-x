package com.skyyo.samples.application.activity

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skyyo.samples.application.Destination
import com.skyyo.samples.application.ProfileGraph
import com.skyyo.samples.extensions.rememberBackStackEntry
import com.skyyo.samples.features.appBarElevation.AppBarElevation
import com.skyyo.samples.features.autofill.AutofillScreen
import com.skyyo.samples.features.autoscroll.AutoScrollScreen
import com.skyyo.samples.features.bottomSheets.BottomSheetScaffoldScreen
import com.skyyo.samples.features.bottomSheets.BottomSheetScreen
import com.skyyo.samples.features.bottomSheets.ModalBottomSheetScreen
import com.skyyo.samples.features.cameraX.CameraXScreen
import com.skyyo.samples.features.cardRecognition.CardRecognitionScreen
import com.skyyo.samples.features.customView.CustomViewScreen
import com.skyyo.samples.features.dominantColor.DominantColorScreen
import com.skyyo.samples.features.dragAndDrop.DragAndDropScreen
import com.skyyo.samples.features.exoPlayer.columnAutoplay.ExoPlayerColumnAutoplayScreen
import com.skyyo.samples.features.exoPlayer.columnDynamicThumb.ExoPlayerColumnDynamicThumbScreen
import com.skyyo.samples.features.exoPlayer.columnIndexed.ExoPlayerColumnIndexedScreen
import com.skyyo.samples.features.exoPlayer.columnReference.ExoPlayerColumnReferenceScreen
import com.skyyo.samples.features.forceTheme.ForceThemeScreen
import com.skyyo.samples.features.googleMap.GoogleMapScreen
import com.skyyo.samples.features.googlePay.GooglePayScreen
import com.skyyo.samples.features.gradientScroll.GradientScrollScreen
import com.skyyo.samples.features.healthConnect.HealthConnectScreen
import com.skyyo.samples.features.imagePicker.ImagePickerScreen
import com.skyyo.samples.features.infiniteViewPager.InfiniteViewPagerScreen
import com.skyyo.samples.features.inputValidations.auto.InputValidationAutoScreen
import com.skyyo.samples.features.inputValidations.autoDebounce.InputValidationAutoDebounceScreen
import com.skyyo.samples.features.inputValidations.manual.InputValidationManualScreen
import com.skyyo.samples.features.languagePicker.LanguagePickerScreen
import com.skyyo.samples.features.lazyColumnWithTextFields.ImeAwareLazyColumnScreen
import com.skyyo.samples.features.marqueeText.MarqueeTextScreen
import com.skyyo.samples.features.navigateWithResult.simple.dogContacts.DogContactsScreen
import com.skyyo.samples.features.navigateWithResult.simple.dogDetails.DogDetailsScreen
import com.skyyo.samples.features.navigateWithResult.simple.dogFeed.DogFeedScreen
import com.skyyo.samples.features.navigateWithResult.withObject.catContacts.CatContactsScreen
import com.skyyo.samples.features.navigateWithResult.withObject.catDetails.CatDetailsScreen
import com.skyyo.samples.features.navigateWithResult.withObject.catFeed.CatFeedScreen
import com.skyyo.samples.features.navigationCores.bottomBar.BottomBarScreen
import com.skyyo.samples.features.navigationCores.drawer.DrawerScreen
import com.skyyo.samples.features.noticeableScrollableRow.NoticeableScrollableRowScreen
import com.skyyo.samples.features.otp.OtpScreen
import com.skyyo.samples.features.pagination.paging.CatsPagingScreen
import com.skyyo.samples.features.pagination.pagingWithDatabase.CatsPagingRoomScreen
import com.skyyo.samples.features.pagination.simple.CatsScreen
import com.skyyo.samples.features.pagination.simpleWithDatabase.CatsRoomScreen
import com.skyyo.samples.features.parallaxEffect.ParallaxEffectScreen
import com.skyyo.samples.features.pdfViewer.PdfViewerScreen
import com.skyyo.samples.features.sampleContainer.SampleContainerScreen
import com.skyyo.samples.features.scanQR.QrScreen
import com.skyyo.samples.features.scanQrNoPermissions.QrNoPermissionsScreen
import com.skyyo.samples.features.scrollAnimation1.ScrollAnimation1Screen
import com.skyyo.samples.features.sharedViewModel.ProfileSharedViewModel
import com.skyyo.samples.features.sharedViewModel.confirmProfile.EditProfileConfirmationScreen
import com.skyyo.samples.features.sharedViewModel.editProfile.EditProfileScreen
import com.skyyo.samples.features.sharedViewModel.profile.ProfileScreen
import com.skyyo.samples.features.signatureView.SignatureViewScreen
import com.skyyo.samples.features.snackbar.SnackbarScreen
import com.skyyo.samples.features.snap.SnapScreen
import com.skyyo.samples.features.stickyHeaders.ListsScreen
import com.skyyo.samples.features.table.TableScreen
import com.skyyo.samples.features.textGradient.TextGradientScreen
import com.skyyo.samples.features.textRecognition.cameraRecognition.CameraTextRecognitionScreen
import com.skyyo.samples.features.textRecognition.imageRecognition.ImageTextRecognitionScreen
import com.skyyo.samples.features.textSpans.TextSpansScreen
import com.skyyo.samples.features.transparentBlur.BlurScreen
import com.skyyo.samples.features.userInteractionTrackingResult.SessionTimeExpiredScreen
import com.skyyo.samples.features.verticalPagerWithFling.VideoPagerWithFlingScreen
import com.skyyo.samples.features.viewPager.ViewPagerScreen
import com.skyyo.samples.features.zoomable.ZoomableScreen

const val TRANSITION_DURATION_LONG = 1000
const val TRANSITION_DURATION_SHORT = 350

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class
)

@Suppress("LongMethod")
@Composable
fun PopulatedNavHost(
    startDestination: String,
    navController: NavHostController,
) = AnimatedNavHost(
    navController = navController,
    startDestination = startDestination,
    enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION_SHORT)) },
    exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION_SHORT)) }
) {
    composable(Destination.SampleContainer.route) { SampleContainerScreen() }
    composable(Destination.Cats.route) { CatsScreen() }
    composable(Destination.CatsRoom.route) { CatsRoomScreen() }
    composable(Destination.CatsPaging.route) { CatsPagingScreen() }
    composable(Destination.CatsPagingRoom.route) { CatsPagingRoomScreen() }
    composable(Destination.Map.route) { GoogleMapScreen() }
    composable(Destination.ForceTheme.route) { ForceThemeScreen() }
    composable(Destination.CameraX.route) { CameraXScreen() }
    bottomSheet(Destination.BottomSheet.route) { BottomSheetScreen() }
    composable(Destination.ModalBottomSheet.route) { ModalBottomSheetScreen() }
    composable(Destination.BottomSheetScaffold.route) { BottomSheetScaffoldScreen() }
    composable(Destination.ViewPager.route) { ViewPagerScreen() }
    composable(Destination.InfiniteViewPager.route) { InfiniteViewPagerScreen() }
    composable(Destination.StickyHeader.route) { ListsScreen() }
    composable(Destination.DogFeed.route) { DogFeedScreen() }
    composable(Destination.DogDetails.route) { DogDetailsScreen() }
    composable(Destination.DogContacts.route) { DogContactsScreen() }
    composable(Destination.CatFeed.route) { CatFeedScreen() }
    composable(Destination.CatDetails.route) { CatDetailsScreen() }
    composable(Destination.CatContacts.route) { CatContactsScreen() }
    composable(
        Destination.InputValidationManual.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { TRANSITION_DURATION_LONG },
                animationSpec = tween(TRANSITION_DURATION_LONG)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -TRANSITION_DURATION_LONG },
                animationSpec = tween(TRANSITION_DURATION_LONG)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -TRANSITION_DURATION_LONG },
                animationSpec = tween(TRANSITION_DURATION_LONG)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { TRANSITION_DURATION_LONG },
                animationSpec = tween(TRANSITION_DURATION_LONG)
            )
        }
    ) { InputValidationManualScreen() }
    composable(Destination.InputValidationAuto.route) { InputValidationAutoScreen() }
    composable(Destination.InputValidationDebounce.route) { InputValidationAutoDebounceScreen() }
    composable(Destination.AppBarElevation.route) { AppBarElevation() }
    composable(Destination.Otp.route) { OtpScreen() }
    composable(Destination.AutoScroll.route) { AutoScrollScreen() }
    composable(Destination.Table.route) { TableScreen() }
    composable(Destination.ParallaxEffect.route) { ParallaxEffectScreen() }
    composable(Destination.CustomView.route) { CustomViewScreen() }
    composable(Destination.SignatureView.route) { SignatureViewScreen() }
    composable(Destination.MarqueeText.route) { MarqueeTextScreen() }
    composable(Destination.Autofill.route) { AutofillScreen() }
    navigation(
        route = ProfileGraph.route,
        startDestination = ProfileGraph.Profile.route
    ) {
        composable(ProfileGraph.Profile.route) { ProfileScreen() }
        composable(ProfileGraph.EditProfile.route) {
            val rootGraphBackStackEntry =
                navController.rememberBackStackEntry(ProfileGraph.Profile.route)
            val navGraphViewModel: ProfileSharedViewModel = hiltViewModel(rootGraphBackStackEntry)
            EditProfileScreen(navGraphViewModel)
        }
        composable(ProfileGraph.ConfirmProfile.route) {
            val rootGraphBackStackEntry =
                navController.rememberBackStackEntry(ProfileGraph.Profile.route)
            val navGraphViewModel: ProfileSharedViewModel = hiltViewModel(rootGraphBackStackEntry)
            EditProfileConfirmationScreen(navGraphViewModel)
        }
    }
    composable(Destination.QrCodeScanning.route) { QrScreen() }
    composable(Destination.QrNoPermissions.route) { QrNoPermissionsScreen() }
    composable(Destination.ScrollAnimation1.route) { ScrollAnimation1Screen() }
    composable(Destination.Snackbar.route) { SnackbarScreen() }
    composable(Destination.Snap.route) { SnapScreen() }
    composable(Destination.BottomBar.route) { BottomBarScreen() }
    composable(Destination.Drawer.route) { DrawerScreen() }
    composable(Destination.GradientScroll.route) { GradientScrollScreen() }
    composable(Destination.NoticeableScrollableRow.route) { NoticeableScrollableRowScreen() }
    composable(Destination.ExoPlayerColumnReference.route) { ExoPlayerColumnReferenceScreen() }
    composable(Destination.ExoPlayerColumnIndexed.route) { ExoPlayerColumnIndexedScreen() }
    composable(Destination.ExoPlayerColumnAutoplay.route) { ExoPlayerColumnAutoplayScreen() }
    composable(Destination.ExoPlayerColumnDynamicThumb.route) { ExoPlayerColumnDynamicThumbScreen() }
    composable(Destination.VerticalPagerWithFling.route) { VideoPagerWithFlingScreen() }
    composable(Destination.DominantColor.route) { DominantColorScreen() }
    composable(Destination.Zoomable.route) { ZoomableScreen() }
    composable(Destination.PdfViewer.route) { PdfViewerScreen() }
    composable(Destination.HealthConnect.route) { HealthConnectScreen() }
    composable(Destination.DragAndDrop.route) { DragAndDropScreen() }
    composable(
        route = Destination.PrivacyPolicy.route,
        deepLinks = listOf(
            navDeepLink {
                action = "androidx.health.ACTION_SHOW_PERMISSIONS_RATIONALE"
            }
        )
    ) {
        PdfViewerScreen()
    }
    composable(Destination.ImagePicker.route) { ImagePickerScreen() }
    composable(Destination.LanguagePicker.route) { LanguagePickerScreen() }
    composable(Destination.CardRecognition.route) { CardRecognitionScreen() }
    composable(Destination.GooglePay.route) { GooglePayScreen() }
    composable(Destination.TextSpans.route) { TextSpansScreen() }
    composable(Destination.ImeAwareLazyColumn.route) { ImeAwareLazyColumnScreen() }
    composable(Destination.TextGradient.route) { TextGradientScreen() }
    composable(Destination.ImageTextRecognition.route) { ImageTextRecognitionScreen() }
    composable(Destination.CameraTextRecognition.route) { CameraTextRecognitionScreen() }
    composable(Destination.Blur.route) { BlurScreen() }
    composable(Destination.SessionTimeExpired.route) { SessionTimeExpiredScreen() }
}
