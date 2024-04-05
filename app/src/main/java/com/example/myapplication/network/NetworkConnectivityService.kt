package com.example.myapplication.network

import com.example.myapplication.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}
