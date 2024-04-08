package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkConnectivityService
import com.example.myapplication.repository.MoviesRepository
import com.example.myapplication.util.NetworkStatus
import com.example.myapplication.util.UiStateApiMovies
import com.example.myapplication.util.UiStateApiSingleMovie
import com.example.myapplication.util.UiStateHomeView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository,
    networkConnectivityService: NetworkConnectivityService,
) : ViewModel() {

    var actualPage = 1
    var firtsFetch = true

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    private val _movies = MutableStateFlow<UiStateApiMovies>(UiStateApiMovies.Loading)

    // Exponer la lista de peliculas como un StateFlow para observar cambios
    val movies = _movies.asStateFlow()

    private val _singleMovie =
        MutableStateFlow<UiStateApiSingleMovie>(UiStateApiSingleMovie.Loading)
    val singleMovie = _singleMovie.asStateFlow()

    private val _uiStateHomeView = MutableStateFlow(
        UiStateHomeView(
            movies,
            "",
            ""
        )
    )
    val uiStateHomeView = _uiStateHomeView.asStateFlow()


    //no se actualiza el contenido si intento hacer fetch en el homeView cada vez que se inicia
    //si hago eso funciona el recargar, pero al segundo intento ya que el loading es solo cuando inicia la app


    fun fetchData() {
        if (firtsFetch) {
            _movies.value = UiStateApiMovies.Loading
            fetchMovies(1)
        }
    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.getMovies(page)
            _movies.value = result

        }
    }

    fun fetchMoreMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            actualPage += 1
            val result = repo.getMovies(actualPage)
            val list =
                (_movies.value as UiStateApiMovies.Success).movieData.plus((result as UiStateApiMovies.Success).movieData)
            _movies.value = UiStateApiMovies.Success(list)
        }
    }


    fun fetchMoviesByName(name: String, year: String?) { //aqui movies tiene que valer loading
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.getMoviesByName(name, year)
            _movies.value = result

        }
    }


    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.getMovieById(id)
            _singleMovie.value = result

        }
    }

    fun onSearchTextChange(text: String) {
        val currentText = _uiStateHomeView.value ?: return
        val updateText = currentText.copy(searchText = text)
        _uiStateHomeView.value = updateText
    }

    fun onFilterChange(year: String) {
        val currentYear = _uiStateHomeView.value ?: return
        val updateYear = currentYear.copy(yearSelected = year)
        _uiStateHomeView.value = updateYear
    }


}









