package com.example.myapplication.util

import com.example.myapplication.model.SingleMovieModel

sealed class UiStateApiMovies {

    data object Loading : UiStateApiMovies()
    data class Success(val singleMovieModel: SingleMovieModel) : UiStateApiMovies()
    data class Error(val message: String) : UiStateApiMovies()
}