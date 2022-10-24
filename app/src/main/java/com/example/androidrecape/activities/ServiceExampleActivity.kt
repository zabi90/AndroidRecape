package com.example.androidrecape.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.androidrecape.databinding.ActivityServiceExampleBinding
import com.example.androidrecape.services.LogService

class ServiceExampleActivity : BaseActivity() {

    private lateinit var binding : ActivityServiceExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_service_example)
        binding = ActivityServiceExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupActionBar("Service Example", true)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.startServiceButton.setOnClickListener {
            startService(Intent(this,LogService::class.java))
        }
    }

    companion object{
        fun getLaunchIntent(context:Context) : Intent {
            return Intent(context,ServiceExampleActivity::class.java)
        }
    }
}