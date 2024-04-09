package com.example.myapplication.util.UiEvents

import com.example.myapplication.model.FavoritosModel

sealed class UiEventDetailView {
    data class AddRemoveFavorite(val inFavorites: Boolean,val favoriteModel : FavoritosModel) : UiEventDetailView()
}