package com.example.wethertestapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat

class LocationProvider(private val mActivity: MainActivity): LocationListener {

    private lateinit var mLocationManager: LocationManager

    init {
        getPermissions()
    }

    private fun getPermissions() {
        if(ActivityCompat.checkSelfPermission(
                mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                mActivity, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            println("Request permissions")

            ActivityCompat.requestPermissions(mActivity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 35)
        }
        else {
            println("All granted")

            mLocationManager = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0f, this)

            val loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            println("coors: " + loc?.latitude)

        }
    }

    @SuppressLint("MissingPermission")
    public fun getLastLocation(): Location? {
        return mLocationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }

    override fun onLocationChanged(location: Location) {
    }
    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    override fun onProviderEnabled(p0: String) {}

    override fun onProviderDisabled(p0: String) {}

}