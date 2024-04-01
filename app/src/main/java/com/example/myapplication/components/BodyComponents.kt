package com.example.myapplication.components


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun MovieCard(titulo: String?, enlace: String?, onClick: () -> Unit, imagenSize: Int){
    ElevatedCard (
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier= Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable { onClick() }
    ) {

        Box {
            //modificar la imagen si no tiene

            if (!enlace.isNullOrEmpty()) {
                AsyncImage(
                    modifier = Modifier
                        .width(200.dp)
                        .height(300.dp)
                        .drawWithContent {
                            // Aplicar gradiente de transparencia
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black),
                                    startY = 600f,
                                    endY = 750f,
                                ),
                                blendMode = BlendMode.DstOut
                            )
                        }
                        ,
                    model = "https://image.tmdb.org/t/p/w500$enlace",
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier
                        .height(imagenSize.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )

            }



            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                text = titulo ?: "",
                fontWeight = FontWeight.ExtraBold,
                color = White,
                textAlign = TextAlign.Center
            )

        }

    }
}

@Composable
fun Spacer15(){
    Spacer(modifier = Modifier.height(15.dp))
}

fun isMovieInFavoritos(favoritosList : List<FavoritosModel>, movieId: Int): Boolean {
    return favoritosList.any { it.id == movieId }
}

@Composable
fun FilterChipExample(title: String, selected: Boolean, newSelected: (Boolean) -> Unit) {



    FilterChip(
        onClick = {
            Log.d("Click en filtro $title", ""+selected)
            newSelected(!selected)
        },
        label = {
            Text(title)
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
        }

    )

}

/*
@Composable
fun FilterChipExample(title: String, selected2: (Boolean, String) -> Unit) {
    var selected by remember { mutableStateOf(false) }


    FilterChip(
        onClick = {
            selected = !selected
            Log.d("Click en filtro ", ""+selected)
            selected2(selected, title)
        },
        label = {
            Text(title)
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
        }
    )
}
 */

@Composable
fun ButtonWithTextField(moviesViewModel: MoviesViewModel) {
    var textValue by remember { mutableStateOf("") }
    var buttonClicked by remember { mutableStateOf(false) }

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
            label = { Text("Buscar") },
            value = textValue,
            onValueChange = { textValue = it },
            colors = textFieldColors,
            textStyle = TextStyle(fontSize = 16.sp) ,// Cambia el tamaño del texto a 16sp,
            singleLine = true
        )

        IconButton(

            onClick = {
                moviesViewModel.fetchMoviesByName(textValue)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Accept"
                )
            },
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (buttonClicked) {
            Text(
                text = "You entered: $textValue",
                modifier = Modifier.padding(16.dp)
            )
        }
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

