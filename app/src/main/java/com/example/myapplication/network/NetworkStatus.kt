package com.example.myapplication.network

sealed class NetworkStatus {
    object Unknown: NetworkStatus()
    object Connected: NetworkStatus()
    object Disconnected: NetworkStatus()
}