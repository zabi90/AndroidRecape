package com.example.androidrecape.activities

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun setupActionBar(title:String,showBack: Boolean){
        if(showBack){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when(item.itemId){
           android.R.id.home -> onBackPressed()
       }
        return super.onOptionsItemSelected(item)
    }
}