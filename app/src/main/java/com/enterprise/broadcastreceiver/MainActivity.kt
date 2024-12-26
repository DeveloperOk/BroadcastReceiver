package com.enterprise.broadcastreceiver

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.enterprise.broadcastreceiver.ui.theme.BroadcastReceiverTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val airplaneModeStateFlow = MutableStateFlow<Boolean?>(null)

        //Medium post broadcast receiver
        //https://medium.com/kouosl/kotlin-andorid-broadcast-receiver-kullan%C4%B1m%C4%B1-302597473f4c
        registerReceiver(AirplaneModeBroadcastReceiver(airplaneModeStateFlow = airplaneModeStateFlow), IntentFilter("android.intent.action.AIRPLANE_MODE"))

        setContent {
            BroadcastReceiverTheme {
                BroadcastReceiverApp(airplaneModeStateFlow = airplaneModeStateFlow)
            }
        }
    }
}



@Composable
fun BroadcastReceiverApp(airplaneModeStateFlow: MutableStateFlow<Boolean?>) {

    val airplaneModeState = airplaneModeStateFlow.collectAsState()

    val airplaneModeStateText = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().background(color = Color.Green)){

        Scaffold(modifier = Modifier.systemBarsPadding().fillMaxSize()) { innerPadding ->

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding).fillMaxSize()
                    .background(color = Color.White)){

                setAirplaneModeStateText(context = context, airplaneModeState = airplaneModeState, airplaneModeStateText = airplaneModeStateText)

                Text(text = stringResource(id = R.string.main_activity_toggle_airplane_mode_info))

                Text(text = airplaneModeStateText.value)

            }

        }

    }

}

fun setAirplaneModeStateText(
    context: Context,
    airplaneModeState: State<Boolean?>,
    airplaneModeStateText: MutableState<String>
) {

    if(airplaneModeState.value == null){

        airplaneModeStateText.value = context.getString(R.string.airplane_mode_unknown)

    }else if(airplaneModeState.value == true){

        airplaneModeStateText.value = context.getString(R.string.airplane_mode_on)

    }else if(airplaneModeState.value == false){

        airplaneModeStateText.value = context.getString(R.string.airplane_mode_off)

    }

}
