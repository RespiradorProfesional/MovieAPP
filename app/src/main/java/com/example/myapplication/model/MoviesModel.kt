package com.example.myapplication.model

data class MoviesModel(

    val results : List<MovieData>
)

data class MovieData(
    val id : Int,
    val original_title : String,
    val overview : String,
    val poster_path: String
)