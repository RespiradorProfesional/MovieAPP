package com.example.myapplication.model




data class SingleMovieModel(
    val title : String?= null,
    val overview : String?= null,
    val poster_path: String? = null,
    val popularity : Double? = null,
    val backdrop_path: String? = null,
    val credits : credits? = null
)

data class credits (
    val cast: List<cast>?
)

data class cast (
    val id : Int?=null,
    val name : String?=null,
    val character : String?=null,
    val profile_path : String?=null
)