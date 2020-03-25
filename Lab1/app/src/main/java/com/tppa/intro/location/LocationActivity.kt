package com.tppa.intro.location

import android.Manifest
import android.app.Application
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tppa.intro.R
import kotlinx.android.synthetic.main.activity_options.optionsApp
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : AppCompatActivity(){

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private val requestPermissionCode = 1000

    private fun checkColor(sharedPreferences: SharedPreferences, key: String) {
        when (sharedPreferences.getString(key, "")) {
            "3" -> {
                locationApp.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "2" -> {
                locationApp.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "1" -> {
                locationApp.setBackgroundColor(Color.parseColor("#B90E0A"))
            }
            else -> {
                locationApp.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            checkColor(sharedPreferences, key)
        }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), requestPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestPermissionCode -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val sharedPreferences: SharedPreferences =
            application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        checkColor(sharedPreferences, "color")

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationView.text = "Latitude: " + location.latitude + "\nLongitude: " + location.longitude + "\nAltitude: " + location.altitude
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0.2f, locationListener)
        else
            requestPermission()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0.2f, locationListener)
        else requestPermission()
    }

    override fun onPause() {
        locationManager.removeUpdates(locationListener)
        super.onPause()
    }

    override fun onStop() {
        locationManager.removeUpdates(locationListener)
        super.onStop()
    }
}
