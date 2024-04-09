package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.repository.FavoritosRepository
import com.example.myapplication.util.UiEvents.UiEventDetailView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(private val repo: FavoritosRepository): ViewModel() {
    private val _favoritosList = MutableStateFlow<List<FavoritosModel>>(emptyList())
    val favoritosList =_favoritosList.asStateFlow()

    fun onEvent(event: UiEventDetailView) {
        when (event) {
            is UiEventDetailView.AddRemoveFavorite -> {
                if (event.inFavorites) deleteFavorito(event.favoriteModel)
                else addFavorito(event.favoriteModel)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO){
            Log.d("se repite " , "s")
            repo.getAllFavoritos().collect{
                    item->
                if (item.isNullOrEmpty()){
                    _favoritosList.value= emptyList()
                } else {
                    _favoritosList.value=item
                }
            }
        }
    }
    fun addFavorito (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.addFavorito(favoritosModel)}
    fun updateFavorito (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.updateFavorito(favoritosModel)}
    fun deleteFavorito (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.deleteFavorito(favoritosModel)}

}