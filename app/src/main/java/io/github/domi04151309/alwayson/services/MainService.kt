package io.github.domi04151309.alwayson.services

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.service.quicksettings.TileService
import io.github.domi04151309.alwayson.alwayson.AlwaysOnQS
import io.github.domi04151309.alwayson.receivers.ChargeInfoReceiver
import io.github.domi04151309.alwayson.receivers.HeadsetInfoReceiver
import io.github.domi04151309.alwayson.receivers.ScreenStateReceiver

class MainService : Service() {

    private val receiverScreen = ScreenStateReceiver()
    private val receiverCharging = ChargeInfoReceiver()
    private val receiverHeadphones = HeadsetInfoReceiver()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        val filterScreen = IntentFilter(Intent.ACTION_SCREEN_ON)
        filterScreen.addAction(Intent.ACTION_SCREEN_OFF)
        val filterCharging = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        val filterHeadphones = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        registerReceiver(receiverScreen, filterScreen)
        registerReceiver(receiverCharging, filterCharging)
        registerReceiver(receiverHeadphones, filterHeadphones)
        TileService.requestListeningState(this, ComponentName(this , AlwaysOnQS::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiverScreen)
        unregisterReceiver(receiverCharging)
        unregisterReceiver(receiverHeadphones)
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}