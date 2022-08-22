package com.example.androidrecape.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationManager(private val context: Context,
                      private val lifecycle: Lifecycle,
                      private val callback: (Location) -> Unit) : DefaultLifecycleObserver {


     var requestingLocationUpdates = false
     var isLocationPermissonAccess = false


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback: LocationCallback
    lateinit var locationSettingTask: Task<LocationSettingsResponse>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        setupClient()

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    // Update UI with location data
                    // ...
                }

                    callback.invoke( locationResult.lastLocation)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (requestingLocationUpdates) startLocationUpdates()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stopLocationUpdates()
    }

    private fun setupClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
    }
    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    location?.let {
                        callback.invoke(it)
                    }
                }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun createLocationRequest() {

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)

        locationSettingTask= client.checkLocationSettings(builder.build())
    }
}