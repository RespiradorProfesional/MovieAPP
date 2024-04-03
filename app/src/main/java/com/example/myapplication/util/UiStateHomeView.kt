package com.example.myapplication.util


import com.example.myapplication.model.MovieData

sealed class UiStateHomeView {

    data object Loading : UiStateHomeView()
    data class Success(val movieData: List<MovieData>) : UiStateHomeView()
    data class Error(val message: String) : UiStateHomeView()
}


//fun updateUi(state:UiStateHomeView){
//    when (state){
//        is UiStateHomeView.Loading-> showLoading()
//        is UiStateHomeView.Success-> showCard()
//        is UiStateHomeView.Error-> showError()
//
//    }
//}