package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.MovieApi
import com.example.myapplication.util.ApiStates.StateApiMovies
import com.example.myapplication.util.ApiStates.StateApiSingleMovie
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieApi:MovieApi){


    suspend fun getMovies(page : Int): StateApiMovies {

        try {
            val responseApi=movieApi.getMovies(page)
            Log.d("Conexion Error " , "no")
            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                StateApiMovies.Success(response)
            }  else {
                StateApiMovies.Error("Error fetching data: ${responseApi.message()}")
            }
    } catch (e: Exception) {
        Log.d("Conexion Error " , "si")
        return StateApiMovies.Error("Exception fetching data: ${e.message}")
    }

    }

    suspend fun getMoviesByName(name :String,year:String?): StateApiMovies {
        try {
            val responseApi=movieApi.getMoviesByName(name,year)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                StateApiMovies.Success(response)
            }  else {
                StateApiMovies.Error("Error fetching data: ${responseApi.message()}")
            }
        } catch (e: Exception) {
            return StateApiMovies.Error("Exception fetching data: ${e.message}")
        }

    }

    suspend fun getMovieById(id:Int): StateApiSingleMovie {

        try {
            val responseApi=movieApi.getMovieById(id)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!
                StateApiSingleMovie.Success(response)
            }  else {
                StateApiSingleMovie.Error("Error fetching data: ${responseApi.message()}")
            }
        }catch (e: Exception) {
            return StateApiSingleMovie.Error("Exception fetching data: ${e.message}")
        }
    }

}