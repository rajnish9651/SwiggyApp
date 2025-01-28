package com.trainee.project.swiggy.location

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.trainee.project.swiggy.R
import java.util.Locale

class MyLocation(
    private val context: FragmentActivity,
    private val listener: OnCityReceivedListener
) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation() {
        var address: String? = null
        if (isLocationEnabled()) {


            if (checkPermissions()) {

                fusedLocationClient.lastLocation.addOnCompleteListener { task ->

                    val location: Location? = task.result

                    location?.let {

                        Log.d("cityy", it.latitude.toString())
                        Log.d("cityy", it.longitude.toString())

                        // get city name from latitude and longitude
                        val city = getCityName(it.latitude, it.longitude)
                        city?.let { cityName ->
                            Log.d("cityy", cityName)
                            listener.onCityReceived(cityName)


                        }
                    } ?: run {
                        // if location is null
                        Toast.makeText(context, "Null location received", Toast.LENGTH_SHORT).show()
                        Log.d("cityy", "null reciever")
                    }

                }

            } else {
                requestPermissions()
            }

        } else {

            enableLocation()
        }


    }

    private fun getCityName(latitude: Double, longitude: Double): String? {

        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(latitude, longitude, 1)
        return address?.get(0)?.getAddressLine(0) ?: address?.get(0)?.subAdminArea

    }

    // function to request permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }


    // function to check if permissions are granted
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    // function to check if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(context, LocationManager::class.java) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // enable location s
    private fun enableLocation() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("To continue, enable location services for better accuracy.")
            .setTitle("Location Disabled")
            .setIcon(R.drawable.location)
            .setPositiveButton("OK") { dialog, which ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("CANCEL") { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }

    // Handling permission requests
    fun onRequestPermissionsResultMyLocation(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                permissionDeniedDialogBox()
            }
        }
    }


    private fun permissionDeniedDialogBox() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("permission is required for this app, please enable it in the app settings")
        builder.setTitle("Location permission denied")
        builder.setPositiveButton("open setting") { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }


    companion object {
        val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }
}