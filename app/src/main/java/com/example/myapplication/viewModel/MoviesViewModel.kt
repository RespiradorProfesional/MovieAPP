package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.SingleMovieModel
import com.example.myapplication.repository.MoviesRepository
import com.example.myapplication.util.UiStateHomeView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
): ViewModel(){

    private val _movies= MutableStateFlow <UiStateHomeView>(UiStateHomeView.Loading)

    // Exponer la lista de peliculas como un StateFlow para observar cambios
    val movies=_movies.asStateFlow()

    var actualPage=1

    private val _singleMovie = MutableStateFlow<SingleMovieModel?>(null)
    val singleMovie = _singleMovie.asStateFlow()


    init {
        fetchMovies(1)
    }

    fun fetchMovies(page: Int){
        viewModelScope.launch{
            withContext(Dispatchers.IO){

                Log.d("Pasa ", "e")
                val result=repo.getMovies(page)
                //si no hay internet no pasa despues del result

                try {
                    _movies.value= UiStateHomeView.Success(result)
                }catch (e: Exception) {
                    _movies.value = UiStateHomeView.Error("Error fetching data")
                }
            }
        }
    }
/*
    fun fetchMoreMovies(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                actualPage+=1
                val result=repo.getMovies(actualPage)
                _movies.value= _movies.value.plus(result ?: emptyList())

            }
        }
    }

    fun fetchMoviesByName(name : String,year: String?){ //aqui movies tiene que valer loading
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                println(name)
                val result=repo.getMoviesByName(name, year)
                _movies.value=result ?: emptyList()
            }
        }
    }

    fun getMovieById(id: Int){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val result=repo.getMovieById(id)
                if (result != null) {
                    _singleMovie.getAndUpdate { result }
                }
            }
        }
    }

 */

}




