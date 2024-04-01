package com.example.myapplication.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.ui.theme.White

@Composable
fun MovieCard(titulo: String?, enlace: String?, onClick: () -> Unit, imagenSize: Int, idMovie: Int?,isFavorite : Boolean){



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
                color = if (isFavorite)Color.Yellow else White,
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
fun ButtonWithTextField(
    onClick: (String) -> Unit) {
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
                onClick(textValue)
            }

                //moviesViewModel.fetchMoviesByName(textValue,"1990") //pasarle tu el onclick
            ,
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

@Composable
fun DropdownList(itemList: List<String>, selectedIndex: Int, onItemClick: (Int,String) -> Unit) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        // button
        Box(
            modifier = Modifier
                .background(Color.Black)
                .clickable { showDropdown = true },
//            .clickable { showDropdown = !showDropdown },
            contentAlignment = Alignment.Center
        ) {
            Text(text = itemList[selectedIndex], modifier = Modifier.padding(3.dp),color= White)
        }

        // dropdown list
        Box() {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                    onDismissRequest = { showDropdown = false }
                ) {

                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .heightIn(max = 90.dp)
                            .verticalScroll(state = scrollState)
                            .border(width = 1.dp, color = Color.Gray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        itemList.onEachIndexed { index, item ->
                            if (index != 0) {
                                Divider(thickness = 1.dp, color = Color.LightGray)
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(index,item)
                                        showDropdown = !showDropdown
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = item, color = White)
                            }
                        }

                    }
                }
            }
        }
    }

}