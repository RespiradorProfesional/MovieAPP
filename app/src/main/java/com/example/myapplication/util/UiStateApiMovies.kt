package com.example.myapplication.util


import com.example.myapplication.model.MovieData

sealed class UiStateApiMovies {

    data object Loading : UiStateApiMovies()
    data class Success(val movieData: List<MovieData>) : UiStateApiMovies()
    data class Error(val message: String) : UiStateApiMovies()
}


//fun updateUi(state:UiStateHomeView){
//    when (state){
//        is UiStateHomeView.Loading-> showLoading()
//        is UiStateHomeView.Success-> showCard()
//        is UiStateHomeView.Error-> showError()
//
//    }
//}