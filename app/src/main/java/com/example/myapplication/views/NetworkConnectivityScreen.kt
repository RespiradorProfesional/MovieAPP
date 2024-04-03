package com.example.myapplication.views

/*
@Composable
fun NetworkConnectivityScreen(
    viewModel: MoviesViewModel,
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    val networkStatus = viewModel.networkStatus.collectAsState()
    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(networkStatus) {
            snackbarHostState.showSnackbar("you are offline")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Connectivity Service")
        }
    }

}*/