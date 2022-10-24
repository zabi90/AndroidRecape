package com.example.androidrecape.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.androidrecape.R
import com.example.androidrecape.activities.UserFeedActivity

class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.
            getActivity(this,0,UserFeedActivity.getLaunchIntent(this),PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.
            getActivity(this,0,UserFeedActivity.getLaunchIntent(this),0)
        }

        val notification = getNotification(pendingIntent)
        startForeground(1,notification)
        Toast.makeText(this, "onStartCommand-Foreground Service", Toast.LENGTH_SHORT).show()

        //fake work
        Handler().postDelayed({
            Toast.makeText(this, "Going to Stop..", Toast.LENGTH_SHORT).show()
            stopForeground(true)
        },10 * 1000)


        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "onCreate-Foreground Service", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy-Foreground Service", Toast.LENGTH_SHORT).show()
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