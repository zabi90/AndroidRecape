package com.example.androidrecape.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class BoundService : Service() {

    private val binder  = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "onCreate : BoundService", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy : BoundService", Toast.LENGTH_SHORT).show()
    }

  inner class MyBinder() : Binder(){
      fun getService() = this@BoundService
  }
}