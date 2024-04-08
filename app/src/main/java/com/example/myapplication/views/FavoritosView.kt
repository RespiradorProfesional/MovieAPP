package com.example.myapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.components.MovieCard
import com.example.myapplication.ui.theme.SecondaryColor
import com.example.myapplication.viewModel.FavoritosViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun FavoritosView(viewModel: FavoritosViewModel, nav: NavController) {
    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize = windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano

    val scrollStateGrid = rememberLazyGridState()

    var searchText by remember { mutableStateOf("") }
    val favoritos by viewModel.favoritosList.collectAsState()
// hacer el uiState


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search movie") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                state = scrollStateGrid,
                columns = GridCells.Fixed(
                    if (mobileSize) 1 else 2
                ),
            ) { val filteredMovie = favoritos.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
                items(filteredMovie) { item ->
                    val inFavorites = favoritos.find { it.id==item.id}!=null
                    MovieCard(item.title, item.poster_path, {
                        nav.navigate("DetailView/${item.id}")
                    }, if (mobileSize) 200 else 230 , item.id,inFavorites)
                }
            }
        }

    }
