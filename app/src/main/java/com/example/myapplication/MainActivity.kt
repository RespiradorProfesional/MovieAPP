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
 * -Bug al deslizar a la izquierda el panel se vuelve lila
 * -El textField se expande fleje si pones muchos caracteres
 * -Al buscar nada o no encontrar nada no se ve nada
 * -Los filtros aun no funcionan, y ni si quiera se actualizan 
 */

/**
 *
 * las string no se ponen a palo seco en los texts
 * la guardas en los recursos
 *
 * no solo aqui sino tambien en otros sitios que se repitan datos
 */
