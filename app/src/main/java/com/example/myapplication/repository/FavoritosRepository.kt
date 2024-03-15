package com.example.myapplication.repository

import com.example.myapplication.model.FavoritosModel
import com.example.myapplication.room.FavoritosDataBaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoritosRepository @Inject constructor(private val favoritosDataBaseDao: FavoritosDataBaseDao) {
    suspend fun addFavorito(favorito: FavoritosModel)=favoritosDataBaseDao.insert(favorito)
    suspend fun updateFavorito(favorito: FavoritosModel)=favoritosDataBaseDao.update(favorito)
    suspend fun deleteFavorito(favorito: FavoritosModel)=favoritosDataBaseDao.delete(favorito)
    fun getAllFavoritos(): Flow<List<FavoritosModel>> = favoritosDataBaseDao.getFavoritos().flowOn(
        Dispatchers.IO).conflate()

}