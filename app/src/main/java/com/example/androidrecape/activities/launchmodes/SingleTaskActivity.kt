package com.example.androidrecape.activities.launchmodes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidrecape.R
import com.example.androidrecape.databinding.ActivitySingleTaskBinding


class SingleTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingleTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySingleTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
           startActivity(LaunchModesActivity.getLaunchIntent(this))
//            val url = "https://www.google.com"
//
//
//            // Create an Intent with ACTION_VIEW and the URL
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.setData(Uri.parse(url))
//
//
//            // Verify that the intent will resolve to an activity
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show()
    }

    companion object{
        fun getLaunchIntent(context: Context) : Intent {
            return Intent(context, SingleTaskActivity::class.java)
        }
    }
}