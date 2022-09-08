package com.skyyo.samples.features.pagination.pagingWithDatabase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skyyo.samples.application.models.Cat
import com.skyyo.samples.common.composables.CircularProgressIndicatorRow
import com.skyyo.samples.extensions.toast
import com.skyyo.samples.features.pagination.common.CatsScreenEvent
import com.skyyo.samples.features.pagination.common.CustomCard
import com.skyyo.samples.features.pagination.common.FadingFab
import com.skyyo.samples.features.pagination.common.PagingException
import com.skyyo.samples.theme.DarkGray
import com.skyyo.samples.theme.White
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun CatsPagingRoomScreen(viewModel: CatsPagingRoomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val insetsStatusBars = WindowInsets.statusBars
    val density = LocalDensity.current

    val insetTop: Dp = remember { insetsStatusBars.getTop(density).dp + 8.dp }
    val listState = rememberLazyListState()
    val isListScrolled by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.receiveAsFlow().flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val cats: LazyPagingItems<Cat> = viewModel.cats.collectAsLazyPagingItems()
    val isRefreshing by remember { derivedStateOf { cats.loadState.refresh is LoadState.Loading } }
    val isErrorOnFirstPage by remember { derivedStateOf { cats.loadState.refresh is LoadState.Error } }
    val isError by remember { derivedStateOf { cats.loadState.append is LoadState.Error } }

    SideEffect {
        if (isErrorOnFirstPage) {
            val errorState = cats.loadState.refresh as LoadState.Error
            viewModel.onCatsLoadingError(errorState.error as PagingException)
            return@SideEffect // Just to prevent 2x toasts
        }
        if (isError) {
            val errorState = cats.loadState.append as LoadState.Error
            viewModel.onCatsLoadingError(errorState.error as PagingException)
        }
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is CatsScreenEvent.ShowToast -> context.toast(event.messageId)
                is CatsScreenEvent.ScrollToTop -> listState.animateScrollToItem(0)
                is CatsScreenEvent.RefreshList -> cats.refresh()
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = viewModel::onSwipeToRefresh,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshingOffset = insetTop,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = DarkGray,
                contentColor = White
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            CatsColumn(listState = listState, cats = cats)
            FadingFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(end = 16.dp),
                isListScrolled = isListScrolled,
                onclick = viewModel::onScrollToTopClick
            )
        }
    }
}

@Composable
fun CatsColumn(
    listState: LazyListState,
    cats: LazyPagingItems<Cat>
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.systemBars
            .only(WindowInsetsSides.Vertical)
            .add(WindowInsets(left = 16.dp, right = 16.dp, bottom = 8.dp))
            .asPaddingValues()
    ) {
        // refreshing on page 0
        if (cats.loadState.refresh is LoadState.Loading) {
            item {
                Text(
                    text = "refreshing on page 0",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        items(cats, Cat::id) { cat -> if (cat != null) CustomCard(catId = cat.id) }

        if (cats.loadState.append is LoadState.Loading) {
            item { CircularProgressIndicatorRow() }
        }

        // invoked when we have no data on initial load
        if (cats.loadState.refresh is LoadState.Error) {
            val errorState = cats.loadState.refresh as LoadState.Error
            val stringRes = (errorState.error as PagingException).stringRes
            item {
                Text(
                    text = stringResource(stringRes),
                    modifier = Modifier.clickable(onClick = cats::retry)
                )
                Text(text = "retry refresh!")
            }
        }

        // invoked when we have no data on page 2,3 etc.
        if (cats.loadState.append is LoadState.Error) {
            val errorState = cats.loadState.append as LoadState.Error
            val stringRes = (errorState.error as PagingException).stringRes
            item {
                Text(
                    text = stringResource(stringRes),
                    modifier = Modifier.clickable(onClick = cats::retry)
                )
                Text(text = "retry append!")
            }
        }
    }
}
