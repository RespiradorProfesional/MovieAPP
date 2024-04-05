package com.example.myapplication.util


import com.example.myapplication.model.MovieData

sealed class UiStateApiSingleMovie {

    data object Loading : UiStateApiSingleMovie()
    data class Success(val movieData: List<MovieData>) : UiStateApiSingleMovie()
    data class Error(val message: String) : UiStateApiSingleMovie()
}


//fun updateUi(state:UiStateHomeView){
//    when (state){
//        is UiStateHomeView.Loading-> showLoading()
//        is UiStateHomeView.Success-> showCard()
//        is UiStateHomeView.Error-> showError()
//
//    }
//}