package com.example.myapplication.util

data class UiStateHomeView(
    val apiMovies: StateApiMovies,
    var searchText: String?,
    var yearSelected: String?
    //, val actualPage : Int
)