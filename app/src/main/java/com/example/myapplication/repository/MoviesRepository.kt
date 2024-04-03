package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.MovieApi
import com.example.myapplication.model.MovieData
import com.example.myapplication.model.SingleMovieModel
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieApi:MovieApi){

    suspend fun getMovies(page : Int): List<MovieData> {
            //Pasa por aqui pero debajo del response no
        Log.d("Entra por aqui " , "no ")
        val response=movieApi.getMovies(page)

        if (response.isSuccessful){
            return response.body()!!.results
        }
        return emptyList()

    }

    suspend fun getMovieById(id:Int): SingleMovieModel?{
        val response=movieApi.getMovieById(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun getMoviesByName(name :String,year:String?): List<MovieData>? {
        val response=movieApi.getMoviesByName(name,year)
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null

    }
}