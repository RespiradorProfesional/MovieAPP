package com.example.myapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.FavoritosModel
import kotlinx.coroutines.flow.Flow

// Interface vamos a crear los métodos,crearemos un repositorio que usará el viewModel que será el que manejará las vistas
//Esta interface será un DAO, Data Access Observer
@Dao
interface FavoritosDataBaseDao {
    //Crud
    @Query("SELECT * FROM favoritos")
    fun getFavoritos(): Flow<List<FavoritosModel>>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insert(favoritosModel: FavoritosModel)

    @Update(onConflict=OnConflictStrategy.REPLACE)
    suspend fun update(favoritosModel:FavoritosModel)

    @Delete
    suspend fun delete (favoritosModel:FavoritosModel)
}