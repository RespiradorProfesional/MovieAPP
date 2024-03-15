package com.example.myapplication.state

data class MovieState(
    val title: String="",
    val overview: String="",
    val poster_path: String="",
    val popularity: Double? =0.00
)