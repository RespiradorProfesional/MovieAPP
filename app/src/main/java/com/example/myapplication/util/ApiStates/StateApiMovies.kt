package com.example.myapplication.util.ApiStates


import com.example.myapplication.model.MovieData

sealed class StateApiMovies {

    data object Loading : StateApiMovies()
    data class Success(val movieData: List<MovieData>) : StateApiMovies()
    data class Error(val message: String) : StateApiMovies()
}


//fun updateUi(state:UiStateHomeView){
//    when (state){
//        is UiStateHomeView.Loading-> showLoading()
//        is UiStateHomeView.Success-> showCard()
//        is UiStateHomeView.Error-> showError()
//
//    }
//}