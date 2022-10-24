package com.example.androidrecape.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class LogService : Service() {

    override fun onBind(intent: Intent): IBinder? {
       return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show()
        //fake work
        Handler().postDelayed({
            stopSelf()
            Toast.makeText(this, "Going to Stop..", Toast.LENGTH_SHORT).show()
        },10 * 1000)
        //Thread.sleep(10000)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }


}