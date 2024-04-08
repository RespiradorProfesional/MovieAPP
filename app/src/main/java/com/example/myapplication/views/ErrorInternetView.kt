package com.example.myapplication.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myapplication.util.NetworkStatus
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun ErrorInternetView(nav: NavController, viewModel: MoviesViewModel, route : String) {

    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()

    val finalRoute = convertStringToFormat(route)

    Log.d("Conexion ", networkStatus.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No hay conexion conexion")
        Button(
            onClick = {

                val currentStatus = networkStatus.value

                Log.d("Ruta " , finalRoute)
                if (currentStatus is NetworkStatus.Connected){
                    if (finalRoute.equals("Home")) viewModel.fetchRetryData()
                    nav.navigate(finalRoute)
                }


            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reintentar conexion")
        }
    }
}

// Función para convertir una cadena en un formato específico
fun convertStringToFormat(input: String): String {
    val builder = StringBuilder()
    var barraPuesta=false
    // Iterar sobre cada carácter de la cadena de entrada
    input.forEachIndexed { index, char ->
        // Si el carácter actual es un dígito y no es el primer carácter
        if (char.isDigit() && index != 0 && !barraPuesta) {
            // Agregar una barra diagonal antes de este dígito
            barraPuesta=true
            builder.append('/')
        }
        // Agregar el carácter actual al resultado
        builder.append(char)
    }

    // Devolver la cadena en el nuevo formato
    return builder.toString()
}
