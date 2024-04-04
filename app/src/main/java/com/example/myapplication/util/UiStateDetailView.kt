package com.example.myapplication.util

import com.example.myapplication.model.SingleMovieModel

sealed class UiStateDetailView {

    data object Loading : UiStateDetailView()
    data class Success(val singleMovieModel: SingleMovieModel) : UiStateDetailView()
    data class Error(val message: String) : UiStateDetailView()
}