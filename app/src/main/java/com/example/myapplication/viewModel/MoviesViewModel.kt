package com.example.myapplication.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.MovieData
import com.example.myapplication.repository.MoviesRepository
import com.example.myapplication.state.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repo: MoviesRepository): ViewModel(){
    private val _movies= MutableStateFlow<List<MovieData>>(emptyList())

    // Exponer la lista de peliculas como un StateFlow para observar cambios
    val movies=_movies.asStateFlow()


    // MutableState para contener el estado actual de la pelicula seleccionada
    var state by mutableStateOf(MovieState())
        private set
    init {
        fetchMovies(1)
    }

    fun fetchMovies(page: Int){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val result=repo.getMovies(page)
                _movies.value=result ?: emptyList()

            }
        }

    }

    fun getMovieById(id: Int){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val result=repo.getMovieById(id)
                if (result != null) {
                    state=state.copy(
                        title = result.title,
                        overview = result.overview,
                        poster_path= result.poster_path,
                        popularity = result.popularity
                    )
                }
            }
        }

    }

}
