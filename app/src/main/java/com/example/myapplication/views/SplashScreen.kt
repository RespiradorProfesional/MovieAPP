package com.example.myapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.PrimaryColor
import com.example.myapplication.ui.theme.SecondaryColor
import com.example.myapplication.ui.theme.White
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(nav: NavController) {

    // Ejecutar la navegación después de un retraso de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        nav.navigate("Home") // Navegar a la pantalla Home
    }

    Surface(color = PrimaryColor) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(SecondaryColor, CircleShape)
                    .graphicsLayer(translationY = 100f)
                    .padding(16.dp)
            )
            {
                Text(
                    text = "MovieApp",
                    color = White
                )
            }
        }
    }
}