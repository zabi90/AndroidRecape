package com.example.androidrecape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidrecape.activities.UserFeedActivity
import com.example.androidrecape.databinding.ActivityMainBinding
import com.example.androidrecape.location.LocationActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bindingClickListener()
    }

    private fun bindingClickListener()
    {
        binding.locationExampleButton.setOnClickListener {
            startActivity(LocationActivity.getLaunchIntent(this))
        }
        binding.viewmodelExampleButton.setOnClickListener {
            startActivity(UserFeedActivity.getLaunchIntent(this))
        }
    }


}