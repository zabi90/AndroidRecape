package com.example.androidrecape.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.androidrecape.R
import com.example.androidrecape.activities.UserFeedActivity
import com.example.androidrecape.location.LocationActivity

class MyIntentService : IntentService("MyIntentService") {


    override fun onCreate() {
        super.onCreate()
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.
            getActivity(this,0, LocationActivity.getLaunchIntent(this),PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.
            getActivity(this,0, LocationActivity.getLaunchIntent(this),0)
        }

        val notification = getNotification(pendingIntent)
        startForeground(12,notification)

    }
    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        /**
         * This is worker thread
         */

        val msg = intent?.extras?.get("msg")
        Log.d("MyIntentService","My Intent Service $msg")
        Thread.sleep(10000)

    }

    private fun getNotification(intent: PendingIntent): Notification {

        val notificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("295","Foreground Service")
        } else {
            ""
        }
        return NotificationCompat.Builder(this,notificationChannel)
            .setSmallIcon(R.drawable.ic_notification_architecture)
            .setContentTitle("Creating Service")
            .setContentText("Foreground service is running")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}