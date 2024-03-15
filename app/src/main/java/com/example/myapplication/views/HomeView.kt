package com.example.myapplication.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication.components.MovieCard
import com.example.myapplication.components.PageButton
import com.example.myapplication.viewModel.MoviesViewModel


@Composable

fun HomeView(viewModel: MoviesViewModel, nav: NavController) {

    val movies by viewModel.movies.collectAsState() //collectAsStateWidth

    LazyColumn(
        Modifier
            .fillMaxWidth()
    ){
        items(movies) {item->
            MovieCard(movie = item, { nav.navigate("DetailView/${item.id}") })

        }
    }

    Row {
        PageButton(1,nav,viewModel)
        PageButton(2,nav,viewModel)
    }


}
