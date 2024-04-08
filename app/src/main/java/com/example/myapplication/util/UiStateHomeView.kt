package com.example.myapplication.util

import kotlinx.coroutines.flow.StateFlow

data class UiStateHomeView(
    val uiApiStateApiMovies: StateFlow<UiStateApiMovies>,
    var searchText: String?,
    var yearSelected: String?
    //, val actualPage : Int
)