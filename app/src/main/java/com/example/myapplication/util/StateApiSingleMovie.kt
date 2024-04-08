package com.example.myapplication.util

import com.example.myapplication.model.SingleMovieModel

sealed class StateApiSingleMovie {

    data object Loading : StateApiSingleMovie()
    data class Success(val singleMovieModel: SingleMovieModel) : StateApiSingleMovie()
    data class Error(val message: String) : StateApiSingleMovie()
}