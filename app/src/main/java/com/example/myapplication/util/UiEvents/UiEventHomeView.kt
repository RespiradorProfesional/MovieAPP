package com.example.myapplication.util.UiEvents

sealed class UiEventHomeView {
    data class SearchMovie(val name: String,val year: String?) : UiEventHomeView()
    data class changeFilter(val year:String) : UiEventHomeView()
}