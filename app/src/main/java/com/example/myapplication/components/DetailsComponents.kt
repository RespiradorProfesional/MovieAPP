package com.example.myapplication.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.ui.theme.PrimaryColor
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.FavoritosViewModel

@Composable
fun profileCard(nombre: String?, personajeNombre: String?, enlace: String?, imagenSize: Int) {

    /**
     * ajustar la carta para que no sea mas larga si el nombre es largo
     * si no tiene personaje que no aparezca el titulo de personaje
     */
    ElevatedCard(
        modifier = Modifier.width(200.dp)
            .height(350.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Spacer15()

        if (!enlace.isNullOrEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .size(imagenSize.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
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

        Spacer15()

        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Nombre",
            color = Color.Gray
        )

        Spacer15()

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = nombre ?: "",
            fontWeight = FontWeight.ExtraBold,
            color = White,
            textAlign = TextAlign.Center
        )

        if(!personajeNombre.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Personaje",
                color = Color.Gray
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = personajeNombre.take(40),
                fontWeight = FontWeight.ExtraBold,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun likeButton(
    viewModelFavorito: FavoritosViewModel,
    favoritosList: List<FavoritosModel>,
    favoritosModel: FavoritosModel
) {

    var isFavorite by remember { mutableStateOf(false) }

    isFavorite = isMovieInFavoritos(favoritosList, favoritosModel.id)

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
            when (isFavorite) {
                true -> viewModelFavorito.deleteFavorito(favoritosModel)
                false -> viewModelFavorito.addFavorito(favoritosModel)
            }

        },


        ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Home else Icons.Outlined.Home,
            contentDescription = "Favorite"
        )
    }
}