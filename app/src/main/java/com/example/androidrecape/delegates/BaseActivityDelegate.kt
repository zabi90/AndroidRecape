package com.example.androidrecape.delegates

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

interface BaseActivityDelegate {
    fun registerActivity(activity: AppCompatActivity)
    fun setupActionBar(title:String,showBack: Boolean)
    fun onOptionsItemsSelected(item: MenuItem): Boolean
}