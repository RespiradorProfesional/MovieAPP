package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.MovieApi
import com.example.myapplication.util.UiStateDetailView
import com.example.myapplication.util.UiStateHomeView
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieApi:MovieApi){

    //que tambien devuelva el sealed class


    suspend fun getMovies(page : Int):  UiStateHomeView{
            //Pasa por aqui pero debajo del response no

        try {
            val responseApi=movieApi.getMovies(page)
            Log.d("Conexion Error " , "no")
            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                UiStateHomeView.Success(response)
            }  else {
                UiStateHomeView.Error("Error fetching data: ${responseApi.message()}")
            }
    } catch (e: Exception) {
        Log.d("Conexion Error " , "si")
        return UiStateHomeView.Error("Exception fetching data: ${e.message}")
    }

    }

    suspend fun getMoviesByName(name :String,year:String?): UiStateHomeView{
        try {
            val responseApi=movieApi.getMoviesByName(name,year)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!.results
                UiStateHomeView.Success(response)
            }  else {
                UiStateHomeView.Error("Error fetching data: ${responseApi.message()}")
            }
        } catch (e: Exception) {
            return UiStateHomeView.Error("Exception fetching data: ${e.message}")
        }

    }

    suspend fun getMovieById(id:Int): UiStateDetailView{

        try {
            val responseApi=movieApi.getMovieById(id)

            return if (responseApi.isSuccessful){
                val response= responseApi.body()!!
                UiStateDetailView.Success(response)
            }  else {
                UiStateDetailView.Error("Error fetching data: ${responseApi.message()}")
            }
        }catch (e: Exception) {
            return UiStateDetailView.Error("Exception fetching data: ${e.message}")
        }
    }

}