package com.example.myapplication.network

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}
