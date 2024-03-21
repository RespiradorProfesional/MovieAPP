package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable



import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.ui.theme.PrimaryColor
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun MovieCard(titulo: String?, enlace: String?, onClick: () -> Unit, imagenSize: Int){
    ElevatedCard (
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier= Modifier
            .clickable { onClick() }
    ) {
        Spacer15()

        //modificar la imagen si no tiene

        Image(
            modifier = Modifier
                .height(imagenSize.dp)
                .fillMaxWidth(),
            painter = painterForNulls(enlace),
            contentDescription = null
        )
        
        Spacer15()

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = titulo ?: "",
            fontWeight = FontWeight.ExtraBold,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer15()

    }
}

@Composable
fun Spacer15(){
    Spacer(modifier = Modifier.height(15.dp))
}

fun isMovieInFavoritos(favoritosList : List<FavoritosModel>, movieId: Int): Boolean {
    return favoritosList.any { it.id == movieId }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipExample() {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text("Filter chip")
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun ButtonWithTextField(moviesViewModel: MoviesViewModel) {
    var textValue by remember { mutableStateOf("") }
    var buttonClicked by remember { mutableStateOf(false) }

    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        backgroundColor = Color.Gray, // Cambia el color de fondo del TextField
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )


    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
        ){

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                colors = textFieldColors,
                textStyle = TextStyle(fontSize = 16.sp) // Cambia el tamaño del texto a 16sp
            )


            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    moviesViewModel.fetchMoviesByName(textValue)
                },
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (buttonClicked) {
            Text(
                text = "You entered: $textValue",
                modifier = Modifier.padding(16.dp)
            )
        }

}

@Composable
fun painterForNulls(enlace: String?): Painter {
    return if (enlace.isNullOrEmpty()){
        painterResource(id = R.drawable.ic_launcher_foreground)
    } else {
        rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500$enlace")
    }
}
