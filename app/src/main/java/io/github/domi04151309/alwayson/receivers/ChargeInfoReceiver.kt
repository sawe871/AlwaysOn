package io.github.domi04151309.alwayson.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import io.github.domi04151309.alwayson.charging.Circle
import io.github.domi04151309.alwayson.charging.Flash
import io.github.domi04151309.alwayson.charging.IOS
import io.github.domi04151309.alwayson.objects.Global

class ChargeInfoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_POWER_CONNECTED) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)

            if (prefs.getBoolean("charging_animation", false)) {
                if (!ScreenStateReceiver.screenIsOn || ScreenStateReceiver.alwaysOnRunning) {
                    if (ScreenStateReceiver.alwaysOnRunning) LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Global.REQUEST_STOP))
                    val i: Intent = when (prefs.getString("charging_style", "circle")) {
                        "ios" -> Intent(context, IOS::class.java)
                        "circle" -> Intent(context, Circle::class.java)
                        else -> Intent(context, Flash::class.java)
                    }
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(i)
                } else if (ScreenStateReceiver.screenIsOn) {
                    Toast.makeText(context, "Power connected", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
