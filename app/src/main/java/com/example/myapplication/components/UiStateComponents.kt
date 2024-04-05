package com.example.myapplication.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myapplication.network.NetworkStatus
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewModel.MoviesViewModel

@Composable
fun showError(message: String,nav:NavController,viewModel:MoviesViewModel) {

    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()

    Log.d("Conexion " , networkStatus.toString())
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = message,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
            Box(
                contentAlignment= Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "No hay conexion conexion")
                Button(onClick = {

                    val currentStatus= networkStatus.value

                    when(currentStatus){
                        is NetworkStatus.Connected->nav.navigate("Home")
                        is NetworkStatus.Unknown-> nav.navigate("Home")
                        is NetworkStatus.Disconnected->nav.navigate("Home")
                    }

                },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Reintentar conexion")
                }
            }
        }
    }


@Composable
fun showLoading(){
    Dialog(
        onDismissRequest = { var showDialog = false },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment= Center,
            modifier = Modifier
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }

}