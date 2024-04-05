package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myapplication.navigation.NavManager
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewModel.FavoritosViewModel
import com.example.myapplication.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelMovies : MoviesViewModel by viewModels()
        val viewModelFavoritos : FavoritosViewModel by viewModels()
        setContent {
            MyApplicationTheme(darkTheme = true) {

                NavManager(viewModelMovies,viewModelFavoritos)

            }
        }
    }
}

/**
 * Cosas a hacer:
 * - Mirar logs de las llamadas api
 * - Comprobar si hay conexion a internet
 * - Catchear en todos las consultas a la api si no hay internet
 * - el repositorio debe devolver directamente el uiState respondiente
 * - Mirar los eventos y como administrarlos
 * - Realizar el UiState en demas pantallas
 */

/**
 * el uiState deberia tener toda la pantalla tmb y tmb el el de la llamada api, buscar sobre UiStates
 */

/**
 *
 * las string no se ponen a palo seco en los texts
 * la guardas en los recursos
 *
 * no solo aqui sino tambien en otros sitios que se repitan datos
 */
