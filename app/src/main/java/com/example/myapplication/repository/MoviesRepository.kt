package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.MovieApi
import com.example.myapplication.util.UiStateApiMovies
import com.example.myapplication.util.UiStateApiSingleMovie
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieApi:MovieApi){


    suspend fun getMovies(page : Int):  UiStateApiMovies{

        try {
            val responseApi=movieApi.getMovies(page)
            Log.d("Conexion Error " , "no")
            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                UiStateApiMovies.Success(response)
            }  else {
                UiStateApiMovies.Error("Error fetching data: ${responseApi.message()}")
            }
    } catch (e: Exception) {
        Log.d("Conexion Error " , "si")
        return UiStateApiMovies.Error("Exception fetching data: ${e.message}")
    }

    }

    suspend fun getMoviesByName(name :String,year:String?): UiStateApiMovies{
        try {
            val responseApi=movieApi.getMoviesByName(name,year)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                UiStateApiMovies.Success(response)
            }  else {
                UiStateApiMovies.Error("Error fetching data: ${responseApi.message()}")
            }
        } catch (e: Exception) {
            return UiStateApiMovies.Error("Exception fetching data: ${e.message}")
        }

    }

    suspend fun getMovieById(id:Int): UiStateApiSingleMovie{

        try {
            val responseApi=movieApi.getMovieById(id)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!
                UiStateApiSingleMovie.Success(response)
            }  else {
                UiStateApiSingleMovie.Error("Error fetching data: ${responseApi.message()}")
            }
        }catch (e: Exception) {
            return UiStateApiSingleMovie.Error("Exception fetching data: ${e.message}")
        }
    }

}