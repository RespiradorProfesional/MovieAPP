package com.example.myapplication.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.network.NetworkStatus
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun NetworkConnectivityScreen(
    viewModel: MoviesViewModel ,
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()
    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(networkStatus) {
            snackbarHostState.showSnackbar("you are offline")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Connectivity Service")
        }
    }

}
