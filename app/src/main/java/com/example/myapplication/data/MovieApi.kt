package com.example.myapplication.data

import com.example.myapplication.model.MoviesModel
import com.example.myapplication.model.SingleMovieModel
import com.example.myapplication.util.Constants.Companion.API_KEY
import com.example.myapplication.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("$ENDPOINT/popular$API_KEY")
    suspend fun getMovies(@Query("page") page: Int): Response<MoviesModel>

    @GET("search/movie$API_KEY")
    suspend fun getMoviesByName(@Query("query") tituloPelicula: String): Response<MoviesModel>

    @GET("$ENDPOINT/{id}$API_KEY&append_to_response=credits")
    suspend fun getMovieById (@Path(value="id")id:Int): Response <SingleMovieModel>

}