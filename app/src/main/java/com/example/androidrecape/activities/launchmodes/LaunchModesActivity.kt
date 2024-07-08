package com.example.androidrecape.activities.launchmodes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidrecape.MainActivity
import com.example.androidrecape.R
import com.example.androidrecape.activities.QoutesActivity
import com.example.androidrecape.databinding.ActivityLaunchModesBinding

class LaunchModesActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchModesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLaunchModesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListener()
    }

    private fun setListener(){
        binding.standardButton.setOnClickListener {
           startActivity(QoutesActivity.getLaunchIntent(this))
        }

        binding.singleTopButton.setOnClickListener {
            startActivity(SingleTopActivity.getLaunchIntent(this))
        }

        binding.singleTaskButton.setOnClickListener {
            startActivity(SingleTaskActivity.getLaunchIntent(this))
        }

        binding.singleInstanceButton.setOnClickListener {

        }
    }

    companion object{
        fun getLaunchIntent(context:Context): Intent{
            return Intent(context, LaunchModesActivity::class.java)
        }
    }
}