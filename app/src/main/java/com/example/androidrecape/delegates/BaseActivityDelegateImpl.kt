package com.example.androidrecape.delegates

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class BaseActivityDelegateImpl : BaseActivityDelegate {

     lateinit var activity: AppCompatActivity
    override fun registerActivity(activity: AppCompatActivity) {
        this.activity = activity
       activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupActionBar(title:String,showBack: Boolean){
        if(showBack){
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        activity.supportActionBar?.title = title
    }

    override fun onOptionsItemsSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> activity.onBackPressed()
        }
        return true
    }


}