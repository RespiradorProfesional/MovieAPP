package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class FavoritosModel(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "poster_path")
    val poster_path :String,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "overview")
    val overview : String,
)
