package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myapplication.navigation.NavManager
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : MoviesViewModel by viewModels()
        setContent {
            MyApplicationTheme {

                NavManager(viewModel)

            }
        }
    }
}

/**
 * Cosas a hacer:
 * -pantalla de carga cuando carga imagenes (en repository se cargan las cosas)
 * -splash imagen cuando inicias la app
 *- hacer que se ajuste en diferentes pantallas (tablet, en tablet si se puede girar
 * en movil 1 fila en tablet 2 y en landscape 3
 * -hacer que no se rote la pantalla
 * -afinar el estilo de la app (y con animacion y todo)
 * -control de errorres
 * -Barra busqueda que cambie poco a poco segun lo que se escriba y en fav el nombre exacto
 * -pagina infinito con Paging3
 * -favoritos con su propia barra de busqueda >:C
 * -favoritos
 * -filtos de busqueda
 * -usar git
 */

/**
 * las string no se ponen a palo seco en los texts
 * la guardas en los recursos
 *
 * no solo aqui sino tambien en otros sitios que se repitan datos
 */
