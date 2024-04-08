package com.example.myapplication.util

import com.example.myapplication.model.SingleMovieModel

sealed class UiStateApiSingleMovie {

    data object Loading : UiStateApiSingleMovie()
    data class Success(val singleMovieModel: SingleMovieModel) : UiStateApiSingleMovie()
    data class Error(val message: String) : UiStateApiSingleMovie()
}