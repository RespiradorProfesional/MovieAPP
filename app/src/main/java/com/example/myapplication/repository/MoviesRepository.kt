package com.example.myapplication.repository

import com.example.myapplication.data.MovieApi
import com.example.myapplication.model.MovieData
import com.example.myapplication.model.SingleMovieModel
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieApi:MovieApi){

    suspend fun getMovies(page : Int): List<MovieData>? {
        val response=movieApi.getMovies(page)
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null

    }

    suspend fun getMovieById(id:Int): SingleMovieModel?{
        val response=movieApi.getMovieById(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun getMoviesByName(name :String): List<MovieData>? {
        val response=movieApi.getMoviesByName(name)
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null

    }
}