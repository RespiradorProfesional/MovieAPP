package com.example.myapplication.views


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.components.ButtonWithTextField
import com.example.myapplication.components.DropdownList
import com.example.myapplication.components.MovieCard
import com.example.myapplication.components.showLoading
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.model.MovieData
import com.example.myapplication.ui.theme.SecondaryColor
import com.example.myapplication.util.UiStateApiSingleMovie
import com.example.myapplication.viewModel.FavoritosViewModel
import com.example.myapplication.viewModel.MoviesViewModel


@Composable
fun HomeView(
    viewModel: MoviesViewModel,
    nav: NavController,
    viewModelFavoritos: FavoritosViewModel
) {


    //pasa por aqui cada vez que se actualiza movies



    viewModel.fetchData()

    val favoritos by viewModelFavoritos.favoritosList.collectAsState()
    val movies by viewModel.movies.collectAsState()


    //pasa por aqui cada vez que se actualiza movies


    Log.d("Conexion peliculas " , movies.toString())


    when (movies) {
        is UiStateApiSingleMovie.Loading -> showLoading()
        is UiStateApiSingleMovie.Success -> showContentHomeView(
            (movies as UiStateApiSingleMovie.Success).movieData,
            favoritos,
            nav,
            viewModel
        )
        is UiStateApiSingleMovie.Error ->nav.navigate("ErrorInternet/Home")
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun showContentHomeView(
    movies: List<MovieData>,
    favoritos: List<FavoritosModel>,
    nav: NavController,
    viewModel: MoviesViewModel
) {

    viewModel.firtsFetch=false
    //Configuracion del movil

    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize =
        windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano

    //items de filtros de años

    val itemList = mutableListOf<String>()

    for (year in 1890..2024) {
        itemList.add(year.toString())
    }

    var selectedIndex by rememberSaveable { mutableStateOf(0) } //investigar como funciona los remember y eso

    var selectedYear: String by remember { mutableStateOf("") }

    //Scroll de las peliculas

    val scrollStateList = rememberLazyListState()

    val isItemReachEndScrollStateList by remember(scrollStateList) {
        derivedStateOf {
            val lastVisibleItem = scrollStateList.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = scrollStateList.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val reachedEnd = lastVisibleItemIndex >= totalItems - 1
            reachedEnd
        }
    }

    LaunchedEffect(key1 = isItemReachEndScrollStateList) {
        if (isItemReachEndScrollStateList) {
            viewModel.fetchMoreMovies()
        }
    }

    //contenido

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = buildAnnotatedString {
                append("Movie\n")
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Cyan, Color.Magenta)
                        )
                    )
                ) {
                    append("App")
                }
            }
        )

        DropdownList(
            itemList = itemList,
            selectedIndex = selectedIndex,
            onItemClick = { index, item ->
                selectedYear = item
                selectedIndex = index
            })


        ButtonWithTextField(
            onClick = {
                viewModel.fetchMoviesByName(
                    it,
                    selectedYear
                ) //el it se lo pasa el ButtonWithTextField a traves del onclick

            })




        Text(
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "Popular"
        )



        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollStateList
        ) {
            items(movies.size) { item ->

                val inFavorites = favoritos.find { it.id == movies.get(item).id } != null
                MovieCard(
                    movies.get(item).title,
                    movies.get(item).poster_path,
                    { nav.navigate("DetailView/${movies.get(item).id}") },
                    if (mobileSize) 200 else 230,
                    movies.get(item).id,
                    inFavorites
                )


            }
        }
    }


}




