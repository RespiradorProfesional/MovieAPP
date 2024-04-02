package com.example.myapplication.util

/*
sealed class UIState {
    data object Loading : UIState()
    data class Success(val data: String) : UIState()
    data class Error(val exception: Exception) : UIState()
}


@Composable
fun updateUI(state: UIState) {
    when (state) {
        is UIState.Loading -> SplashScreen()
        is UIState.Success -> showData(state.data)
        is UIState.Error -> showError(state.exception)
    }
}



 */