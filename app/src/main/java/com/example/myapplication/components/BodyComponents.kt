package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.model.MovieData
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun MovieCard(movie: MovieData, onClick: ()->Unit){

    Card (
        shape= RoundedCornerShape(5.dp),
        modifier= Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .clickable { onClick() }
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500"+movie.poster_path),
            contentDescription = null
        )


        Text(
            text = movie.original_title,
            fontWeight = FontWeight.ExtraBold,
            color = White,
        )


    }
}

@Composable
fun PageButton(page: Int, nav: NavController, viewModel: MoviesViewModel){

    Button(
        onClick = {
            viewModel.fetchMovies(page) //hice fetch movies publico
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "$page")
    }
}


