package com.example.myapplication.util.UiStates

import com.example.myapplication.util.ApiStates.StateApiMovies

data class UiStateHomeView(
    val apiMovies: StateApiMovies,
    var searchText: String?,
    var yearSelected: String?
    //, val actualPage : Int
)