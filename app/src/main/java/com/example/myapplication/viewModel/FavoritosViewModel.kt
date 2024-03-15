package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.repository.FavoritosRepository
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

    init {
        viewModelScope.launch(Dispatchers.IO){
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
    fun addCrono (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.addFavorito(favoritosModel)}
    fun updateCrono (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.updateFavorito(favoritosModel)}
    fun deleteCrono (favoritosModel: FavoritosModel)=viewModelScope.launch{repo.deleteFavorito(favoritosModel)}

}