package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.viewModel.MoviesViewModel
import com.example.myapplication.views.DetailView
import com.example.myapplication.views.HomeView

@Composable
fun NavManager(viewModel:MoviesViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){

        composable("Home"){

            HomeView(viewModel, navController)
        }

        composable("DetailView/{id}", arguments= listOf(
            navArgument("id"){
                type= NavType.IntType
            }
        )){
            val id = it.arguments?.getInt("id")?:0
            DetailView(viewModel, navController, id)
        }
    }
}
