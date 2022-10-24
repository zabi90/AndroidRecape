package com.example.androidrecape.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidrecape.activities.BaseActivity
import com.example.androidrecape.databinding.ActivityLocationBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationActivity : BaseActivity() {
    private val REQUEST_CHECK_SETTINGS = 121

    lateinit var binding: ActivityLocationBinding
    private lateinit var locationManager: LocationManager



    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar("Location Example", true)
        locationManager = LocationManager(this,lifecycle){
            binding.locationTextView.text = "${it.latitude}, ${it.longitude}"
        }
        lifecycle.addObserver(locationManager)

        bindingClickListener()
        getLocationPermission()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindingClickListener() {

        binding.buttonLastLocation.setOnClickListener {
            if(locationManager.isLocationPermissonAccess){
                locationManager.getLastLocation()
            }
        }

        binding.locationUpdateButton.setOnClickListener {
            if(locationManager.isLocationPermissonAccess){
                createLocationRequest()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                locationManager.isLocationPermissonAccess = true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                //showInContextUI(...)
                Toast.makeText(this, "We need location dear", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.

    private val requestPermissionLauncher =
            registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    locationManager.isLocationPermissonAccess = true
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }




    @RequiresApi(Build.VERSION_CODES.M)
    private fun createLocationRequest() {

        locationManager.createLocationRequest()
        locationManager.locationSettingTask.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            Toast.makeText(this@LocationActivity, "Setting On", Toast.LENGTH_SHORT).show()
            locationManager.requestingLocationUpdates = true
            locationManager.startLocationUpdates()
        }

        locationManager.locationSettingTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().

                    exception.startResolutionForResult(this@LocationActivity,
                            REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    locationManager.requestingLocationUpdates = false
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            Toast.makeText(this@LocationActivity, "Setting On from result", Toast.LENGTH_SHORT).show()
            locationManager.requestingLocationUpdates = true
            locationManager.startLocationUpdates()
        }
    }


    companion object {

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, LocationActivity::class.java)
        }
    }
}