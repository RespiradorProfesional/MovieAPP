package com.example.myapplication.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication.components.MovieCard
import com.example.myapplication.components.PageButton
import com.example.myapplication.components.Separacion
import com.example.myapplication.ui.theme.PrimaryColor
import com.example.myapplication.viewModel.MoviesViewModel


@Composable
fun HomeView(viewModel: MoviesViewModel, nav: NavController) {

    val scrollState = rememberLazyListState()
    val isItemReachEndScroll by remember(scrollState) {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = scrollState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val reachedEnd = lastVisibleItemIndex >= totalItems - 1
            reachedEnd
        }
    }

    LaunchedEffect(key1 = isItemReachEndScroll) {
        if (isItemReachEndScroll) {
            viewModel.fetchMoreMovies()
        }
    }

    val movies by viewModel.movies.collectAsState()

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = PrimaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(movies) { item ->
            MovieCard(movie = item, { nav.navigate("DetailView/${item.id}") })
            Separacion(20)
        }
    }

    Row {
        PageButton(1, nav, viewModel)
        PageButton(2, nav, viewModel)
    }
}
