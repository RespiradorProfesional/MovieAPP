package com.example.myapplication.model

data class MoviesModel(

    val results : List<MovieData>
)

data class MovieData(
    val id : Int?= null,
    val title : String?= null,
    val overview : String?= null,
    val poster_path: String? = null
)