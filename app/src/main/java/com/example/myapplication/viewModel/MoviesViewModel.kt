package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkConnectivityService
import com.example.myapplication.repository.MoviesRepository
import com.example.myapplication.util.NetworkStatus
import com.example.myapplication.util.StateApiMovies
import com.example.myapplication.util.StateApiSingleMovie
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

    var actualPage = 1
    var firtsFetch = true

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

//    private val _movies = MutableStateFlow<StateApiMovies>(StateApiMovies.Loading)
//
//    val movies = _movies

    private val _uiStateHomeView = MutableStateFlow(
        UiStateHomeView(
            StateApiMovies.Loading,
            "",
            ""
        )
    )
    val uiStateHomeView = _uiStateHomeView.asStateFlow()

    private val _uiStateDetailView= MutableStateFlow(
        UiStateDetailView(
            StateApiSingleMovie.Loading
        )
    )

    val uiStateDetailView = _uiStateDetailView.asStateFlow()

    //no se actualiza el contenido si intento hacer fetch en el homeView cada vez que se inicia
    //si hago eso funciona el recargar, pero al segundo intento ya que el loading es solo cuando inicia la app

    init {
        fetchMovies(1)
    }

    fun fetchRetryData() {
        if (firtsFetch) {
            _uiStateHomeView.value = _uiStateHomeView.value.copy(
                apiMovies = StateApiMovies.Loading
            )
            fetchMovies(1)
        }
    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.getMovies(page)
            //_uiStateHomeView.getAndUpdate { it.copy(apiMovies = result) }
            _uiStateHomeView.value = _uiStateHomeView.value.copy(
                apiMovies = result
            )
        }
    }

    fun fetchMoreMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            actualPage += 1
            val result = repo.getMovies(actualPage)
            val list = (_uiStateHomeView.value.apiMovies as StateApiMovies.Success).movieData.plus((result as StateApiMovies.Success).movieData)

            _uiStateHomeView.value = _uiStateHomeView.value.copy(
                apiMovies = StateApiMovies.Success(list)
            )

        }
    }


    fun fetchMoviesByName(name: String, year: String?) { //aqui movies tiene que valer loading
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.getMoviesByName(name, year)
            _uiStateHomeView.value = _uiStateHomeView.value.copy(
                apiMovies = result
            )

        }
    }


    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {


            val result = repo.getMovieById(id)

            _uiStateDetailView.value= _uiStateDetailView.value.copy(
                apiMovies = result
            )

        }
    }

    fun onSearchTextChange(text: String) {
        val currentText = _uiStateHomeView.value
        val updateText = currentText.copy(searchText = text)
        _uiStateHomeView.value = updateText
    }

    fun onFilterChange(year: String) {
        val currentYear = _uiStateHomeView.value
        val updateYear = currentYear.copy(yearSelected = year)
        _uiStateHomeView.value = updateYear
    }


}









