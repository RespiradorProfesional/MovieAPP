package com.example.myapplication.views


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
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
import com.example.myapplication.components.FilterChipExample
import com.example.myapplication.components.MovieCard
import com.example.myapplication.ui.theme.SecondaryColor
import com.example.myapplication.viewModel.MoviesViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeView(viewModel: MoviesViewModel, nav: NavController) {

    val configuration = LocalConfiguration.current //coge la configuracion del movil actual
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    //en tamaño Dp se calcula del tamaño tanto alto como ancho del movil

    val windowSizeClass1 = WindowSizeClass.calculateFromSize(size) //lo calcula

    val mobileSize =
        windowSizeClass1.widthSizeClass == WindowWidthSizeClass.Compact //esto es un booleano

    val scrollStateGrid = rememberLazyGridState()

    val isItemReachEndScrollGridCells by remember(scrollStateGrid) {
        derivedStateOf {
            val lastVisibleItem = scrollStateGrid.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = scrollStateGrid.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val reachedEnd = lastVisibleItemIndex >= totalItems - 1
            reachedEnd
        }
    }

    LaunchedEffect(key1 = isItemReachEndScrollGridCells) {
        if (isItemReachEndScrollGridCells) {
            viewModel.fetchMoreMovies()
        }
    }

    val items by viewModel.items.collectAsState()

    val movies by viewModel.movies.collectAsState()


    /**
     * al clickar este se posiciona el primero y esta en true, pero el aspecto de true lo recibe otro en la forma visual
     */

    // Contenedor para el TextField y el FilterChip
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


        ButtonWithTextField(viewModel)


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val sortedItems = items.sortedByDescending { it.selected }  //funciona raro, ya que no se detecta del todo cuando se activa o no
            items(sortedItems) { item ->
                FilterChipExample(
                    item.title,
                    selected2 = { newSelected ->
                        val updatedItems =
                            items.map { if (it.title == item.title) it.copy(selected = newSelected) else it }
                        viewModel.setItems(updatedItems)
                    }
                )
            }

        }

//            for (item in items) {
//                Log.d("Filtro en for" , ""+item.selected)
//                if (item.selected){
//                    FilterChipExample(item.title,
//                        selected2 = { isSelected, title ->  //los Unit te permiten cambiar al padre segun el parametro, aqui recogen el selected 2 pasado por la funcion
//                            if (isSelected) {
//
//                                items.first { it.title.equals(title)}.selected = isSelected
//                            }
//                        })
//                }else{
//                    FilterChipExample(item.title,
//                        selected2 = { isSelected, title ->  //los Unit te permiten cambiar al padre segun el parametro
//                            if (!isSelected) {
//                                Log.d("Is selected", "Seleccionado")
//                                items.first { it.title .equals(title)  }.selected = isSelected
//                            }
//                        })
//                }
//
//            }


        Text(
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "Popular"
        )


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { item ->
                MovieCard(
                    item.title, item.poster_path, { nav.navigate("DetailView/${item.id}") },
                    if (mobileSize) 200 else 230
                )

            }
        }

    }
}





