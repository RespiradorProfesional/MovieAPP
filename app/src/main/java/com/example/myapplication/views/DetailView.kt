package com.example.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication.state.MovieState
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.MoviesViewModel


@Composable
fun DetailView (viewModel: MoviesViewModel, nav : NavController, id:Int){
    LaunchedEffect(Unit) {
        // Llama a la función para obtener los detalles del anime al inicializar la vista.
        viewModel.getMovieById(id)
    }



    ContentView(viewModel)
}

@Composable
fun ContentView(viewModel: MoviesViewModel){

    val movie = viewModel.state

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(8.dp)) {
        // Utiliza Column para organizar verticalmente el contenido de la vista
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState(movie.hashCode()))
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500"+movie.poster_path),
                contentDescription = null
            )

            Text(
                text = movie.title,
                fontWeight = FontWeight.ExtraBold,
                color = White
            )

            Text(
                text = movie.overview,
                color = White
            )
        }
    }
}