package com.enterprise.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow


class AirplaneModeBroadcastReceiver(val airplaneModeStateFlow: MutableStateFlow<Boolean?>) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val state = intent.getBooleanExtra("state", false)

        airplaneModeStateFlow.value = state

    }


}