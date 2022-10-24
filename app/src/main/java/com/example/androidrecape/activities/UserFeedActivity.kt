package com.example.androidrecape.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.androidrecape.databinding.ActivityUserFeedBinding
import com.example.androidrecape.delegates.BaseActivityDelegate
import com.example.androidrecape.delegates.BaseActivityDelegateImpl
import com.example.androidrecape.viewmodels.UserFeedViewModel

class UserFeedActivity : AppCompatActivity(), BaseActivityDelegate by BaseActivityDelegateImpl() {

    private lateinit var binding: ActivityUserFeedBinding
    private val model: UserFeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_user_feed)

        binding = ActivityUserFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        registerActivity(this)
        setupActionBar("User", true)

        model.getUsers().observe(this, Observer { users ->
            // update UI
            Toast.makeText(this@UserFeedActivity, users[0].description, Toast.LENGTH_SHORT).show()
        })

        binding.loadButton.setOnClickListener {
            model.loadUsers()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onOptionsItemsSelected(item)
    }
    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, UserFeedActivity::class.java)
        }
    }
}
