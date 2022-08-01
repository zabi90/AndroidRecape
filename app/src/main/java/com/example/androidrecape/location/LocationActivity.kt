package com.example.androidrecape.location

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidrecape.R

class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }

    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, LocationActivity::class.java)
        }
    }
}