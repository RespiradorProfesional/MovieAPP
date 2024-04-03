package com.example.myapplication.views

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import com.example.myapplication.viewModel.FavoritosViewModel
import com.example.myapplication.viewModel.MoviesViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DetailView(viewModelMovies: MoviesViewModel, viewModelFavoritos: FavoritosViewModel, id: Int) {
/*
    viewModelMovies.getMovieById(id)
    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize =
        windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano

    if (mobileSize) ContentViewMobile(
        viewModelMovies,
        viewModelFavoritos,
        id
    ) else ContentViewMobile(viewModelMovies, viewModelFavoritos, id)



 */
}

/*
@Composable
fun ContentViewMobile(
    viewModelMovies: MoviesViewModel,
    viewModelFavoritos: FavoritosViewModel,
    id: Int
) {

    val movie by viewModelMovies.singleMovie.collectAsState()
    val favoritos by viewModelFavoritos.favoritosList.collectAsState()
    //box dentro de la columna


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState(movie.hashCode()))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {

        // Utiliza Column para organizar verticalmente el contenido de la vista

        Header(
            posterImage = movie?.poster_path,
            backgroundImage = movie?.backdrop_path
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 20.sp,
            text = movie?.title ?: "",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            color = White,
        )


        Text(
            textAlign = TextAlign.Center,
            text = movie?.overview ?: "",
            color = White
        )


        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Cast",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            color = White,
        )



        likeButton(
            viewModelFavorito = viewModelFavoritos, favoritosList = favoritos, favoritosModel =
            FavoritosModel(
                id = id,
                poster_path = movie?.poster_path ?: "",
                title = movie?.title ?: "",
                overview = movie?.overview ?: ""
            )
        )

        val castList = movie?.credits?.cast

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(castList ?: emptyList()) { actor ->

                profileCard(actor.name, actor.character, actor.profile_path, 150)
            }
        }

    }

}

@Composable
private fun Header(
    posterImage: String?,
    backgroundImage: String?
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9F),
            painter = painterForNulls(backgroundImage),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(top = 100.dp),
            painter = painterForNulls(posterImage),
            contentDescription = null
        )
    }
}*/

