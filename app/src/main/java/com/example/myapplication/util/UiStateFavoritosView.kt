package com.example.myapplication.util

import com.example.myapplication.model.FavoritosModel

data class UiStateFavoritosView (
    var searchText: String?,
    val favoritesRoom : List<FavoritosModel>
)