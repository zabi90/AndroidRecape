package com.example.androidrecape.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.androidrecape.databinding.ActivityServiceExampleBinding
import com.example.androidrecape.services.BoundService
import com.example.androidrecape.services.ForegroundService
import com.example.androidrecape.services.LogService

class ServiceExampleActivity : BaseActivity() {

    private lateinit var binding : ActivityServiceExampleBinding
    private var isServiceBounded = false
    private lateinit var boundService: BoundService
    private lateinit var serviceConnection: MyServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_service_example)
        binding = ActivityServiceExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupActionBar("Service Example", true)
        setOnClickListener()
        serviceConnection = MyServiceConnection()
    }

    private fun setOnClickListener(){
        binding.startServiceButton.setOnClickListener {
            startService(Intent(this,LogService::class.java))
        }

        binding.foregroundServiceButton.setOnClickListener {
            //ContextCompat.startForegroundService(Intent(this,ForegroundService::class.java))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this,ForegroundService::class.java))
            }else{
                startService(Intent(this,ForegroundService::class.java))
            }
        }

        binding.startBoundServiceButton.setOnClickListener {

            if (!isServiceBounded){
                Intent(this,BoundService::class.java).also {
                    bindService(it,serviceConnection,Context.BIND_AUTO_CREATE)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
        isServiceBounded = false
    }

    //Service Connection
    inner class MyServiceConnection : ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as BoundService.MyBinder
            boundService = binder.getService()
            isServiceBounded = true
            Toast.makeText(this@ServiceExampleActivity, "onServiceConnected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceBounded = false
            Toast.makeText(this@ServiceExampleActivity, "onServiceDisconnected", Toast.LENGTH_SHORT).show()
        }

    }

    companion object{
        fun getLaunchIntent(context:Context) : Intent {
            return Intent(context,ServiceExampleActivity::class.java)
        }
    }
}