package com.example.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.components.BottomNavigationItem
import com.example.myapplication.components.isMovieInFavoritos
import com.example.myapplication.components.painterForNulls
import com.example.myapplication.components.profileCard
import com.example.myapplication.components.showLoading
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.model.SingleMovieModel
import com.example.myapplication.ui.theme.White
import com.example.myapplication.util.ApiStates.StateApiSingleMovie
import com.example.myapplication.util.UiEvents.UiEventDetailView
import com.example.myapplication.viewModel.FavoritosViewModel
import com.example.myapplication.viewModel.MoviesViewModel


@Composable
fun DetailView(
    viewModelMovies: MoviesViewModel,
    viewModelFavoritos: FavoritosViewModel,
    id: Int,
    nav: NavController
) {

    viewModelMovies.getMovieById(id)

    val uiStateDetailView by viewModelMovies.uiStateDetailView.collectAsState()

    when (uiStateDetailView.apiMovies) {
        is StateApiSingleMovie.Loading -> showLoading()
        is StateApiSingleMovie.Success -> showContentDetailView(
            (uiStateDetailView.apiMovies as StateApiSingleMovie.Success).singleMovieModel,
            viewModelFavoritos,
            id
        )

        is StateApiSingleMovie.Error -> nav.navigate("ErrorInternet/DetailView$id")
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun showContentDetailView(
    movie: SingleMovieModel,
    viewModelFavoritos: FavoritosViewModel,
    id: Int
) {

    val favoritosModel = FavoritosModel(
        id = id,
        poster_path = movie?.poster_path ?: "",
        title = movie?.title ?: "",
        overview = movie?.overview ?: ""
    )

    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize =
        windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano


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

        var isFavorite by remember { mutableStateOf(false) }

        isFavorite = isMovieInFavoritos(favoritos, favoritosModel.id)

        BottomNavigationItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        )

        Button(
            modifier = Modifier
                .padding(8.dp),
            // .align(Alignment.CenterHorizontally),
            onClick = {
                      viewModelFavoritos.onEvent(UiEventDetailView.AddRemoveFavorite(isFavorite,favoritosModel))
            },


            ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Home else Icons.Outlined.Home,
                contentDescription = "Favorite"
            )
        }

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
}

