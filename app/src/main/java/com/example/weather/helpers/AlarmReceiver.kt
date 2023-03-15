package com.example.weather.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.weather.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val string=intent.getStringExtra(Consts.WETDESC).toString()
        val newIntent=Intent(context,AlarmActivity::class.java)
        newIntent.putExtra(Consts.WETDESC,string)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent)

    }
}