package com.example.myapplication.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.components.DropdownList
import com.example.myapplication.components.MovieCard
import com.example.myapplication.components.showLoading
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.ui.theme.SecondaryColor
import com.example.myapplication.util.ApiStates.StateApiMovies
import com.example.myapplication.util.UiEvents.UiEventHomeView
import com.example.myapplication.util.UiStates.UiStateHomeView
import com.example.myapplication.viewModel.FavoritosViewModel
import com.example.myapplication.viewModel.MoviesViewModel


@Composable
fun HomeView(
    viewModel: MoviesViewModel,
    nav: NavController,
    viewModelFavoritos: FavoritosViewModel
) {


    //pasa por aqui cada vez que se actualiza movies


    //quitar esto y hacerlo con el init

    val favoritos by viewModelFavoritos.favoritosList.collectAsState()

    val uiStateHomeView by viewModel.uiStateHomeView.collectAsState()

    //pasa por aqui cada vez que se actualiza movies


    when (uiStateHomeView.apiMovies) {
        is StateApiMovies.Loading -> showLoading()
        is StateApiMovies.Success -> showContentHomeView(
            uiStateHomeView,
            favoritos,
            nav,
            viewModel
        )

        is StateApiMovies.Error -> nav.navigate("ErrorInternet/Home")
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun showContentHomeView(
    uiStateHomeView: UiStateHomeView,
    favoritos: List<FavoritosModel>,
    nav: NavController,
    viewModel: MoviesViewModel
) {

    var movies = (uiStateHomeView.apiMovies as StateApiMovies.Success).movieData
    viewModel.firtsFetch = false
    //Configuracion del movil

    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize =
        windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano

    //items de filtros de años

    val itemList = mutableListOf<String>()

    for (year in 1895..2024) {
        itemList.add(year.toString())
    }


    var selectedIndex by rememberSaveable { mutableStateOf(0) } //investigar como funciona los remember y eso

//    var selectedYear: String by remember { mutableStateOf("") }

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
                viewModel.onEvent(UiEventHomeView.changeFilter(item))
                selectedIndex = index
            })


        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White, // Color de la línea cuando el TextField está enfocado
            textColor = Color.White,
            backgroundColor = Color.Black, // Cambia el color de fondo del TextField
        )


        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

//34
            OutlinedTextField(
                visualTransformation = VisualTransformation.None,
                label = { androidx.compose.material3.Text("Buscar") },
                value = uiStateHomeView.searchText ?: "",
                onValueChange = { viewModel.onSearchTextChange(it) },
                colors = textFieldColors,
                textStyle = TextStyle(fontSize = 16.sp),// Cambia el tamaño del texto a 16sp,
                singleLine = true
            )

            IconButton(
                onClick = {
                    viewModel.onEvent(UiEventHomeView.SearchMovie(uiStateHomeView.searchText ?: "", uiStateHomeView.yearSelected))
                },

                //moviesViewModel.fetchMoviesByName(textValue,"1990") //pasarle tu el onclick
                content = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Accept"
                    )
                },
            )
        }



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




