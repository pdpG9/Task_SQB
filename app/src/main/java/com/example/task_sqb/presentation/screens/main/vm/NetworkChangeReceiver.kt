package com.example.task_sqb.presentation.screens.main.vm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.task_sqb.utils.isConnected
import com.example.task_sqb.utils.networkStateFlow

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            Toast.makeText(it, "Internet connection is ${isConnected(it)}!", Toast.LENGTH_SHORT)
                .show()
            networkStateFlow.value = isConnected(it)
        }
    }
}