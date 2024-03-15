package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.FavoritosModel

@Database(entities = [FavoritosModel::class], version=1, exportSchema=false)
abstract class FavoritosDataBase: RoomDatabase() {

    abstract fun favoritosDao(): FavoritosDataBaseDao


}