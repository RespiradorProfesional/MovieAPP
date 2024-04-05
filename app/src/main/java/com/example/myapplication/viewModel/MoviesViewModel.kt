package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkConnectivityService
import com.example.myapplication.network.NetworkStatus
import com.example.myapplication.repository.MoviesRepository
import com.example.myapplication.util.UiStateDetailView
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

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    private val _movies = MutableStateFlow<UiStateHomeView>(UiStateHomeView.Loading)

    // Exponer la lista de peliculas como un StateFlow para observar cambios
    val movies = _movies.asStateFlow()

    var actualPage = 1

    private val _singleMovie = MutableStateFlow<UiStateDetailView>(UiStateDetailView.Loading)
    val singleMovie = _singleMovie.asStateFlow()


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
                (_movies.value as UiStateHomeView.Success).movieData.plus((result as UiStateHomeView.Success).movieData)
            _movies.value = UiStateHomeView.Success(list)
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
            _singleMovie.value=result

        }
    }


}









