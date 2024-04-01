package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.components.FilterItem
import com.example.myapplication.model.MovieData
import com.example.myapplication.model.SingleMovieModel
import com.example.myapplication.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repo: MoviesRepository): ViewModel(){
    private val _movies= MutableStateFlow<List<MovieData>>(emptyList())

    // Exponer la lista de peliculas como un StateFlow para observar cambios
    val movies=_movies.asStateFlow()

    var actualPage=1;

    var _items =  MutableStateFlow(
        listOf(
            FilterItem("Terror", false),
            FilterItem("Accion", false),
            FilterItem("Aventura", false)
        )
    )

    val items=_items.asStateFlow()



    private val _singleMovie = MutableStateFlow<SingleMovieModel?>(null)
    val singleMovie = _singleMovie.asStateFlow()


    init {
        fetchMovies(1)
    }

    fun setItems(newList: List<FilterItem>){
        val sortedItems = newList.sortedByDescending { it.selected }
        _items.value=sortedItems
    }


    fun fetchMovies(page: Int){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val result=repo.getMovies(page)
                _movies.value=result ?: emptyList()

            }
        }
    }

    fun fetchMoreMovies(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                actualPage+=1
                val result=repo.getMovies(actualPage)
                _movies.value= _movies.value.plus(result ?: emptyList())

            }
        }
    }

    fun fetchMoviesByName(name : String){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                println(name)
                val result=repo.getMoviesByName(name)
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

}
