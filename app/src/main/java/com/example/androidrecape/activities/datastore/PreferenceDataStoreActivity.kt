package com.example.androidrecape.activities.datastore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidrecape.databinding.ActivityPreferenceDataStoreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreferenceDataStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityPreferenceDataStoreBinding
    lateinit var preferenceDataStoreRepository: PreferenceDataStoreRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceDataStoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferenceDataStoreRepository = PreferenceDataStoreRepository(this)
        setListener()
    }

    private fun setListener() {
        binding.saveButton.setOnClickListener {

            lifecycleScope.launch {

                withContext(Dispatchers.IO) {
                    preferenceDataStoreRepository.saveUserName(binding.nameTextField.text.toString())
                }

                withContext(Dispatchers.Main) {
                    preferenceDataStoreRepository.getUserName().catch {

                    }.collect {
                        Toast.makeText(
                            this@PreferenceDataStoreActivity,
                            "Username : ${it ?: ""}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, PreferenceDataStoreActivity::class.java)
        }
    }
}