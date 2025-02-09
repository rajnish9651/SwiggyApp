package com.trainee.project.swiggy.location

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.notification.Notification
import java.util.Locale

class MyLocation(
    private val context: Context,

    ) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation() {
        if(isNetworkAvailable()){
            if (isLocationEnabled()) {
                if (checkPermissions()) {
                    fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                        try {
                            val location: Location? = task.result
                            location?.let {
                                getCityName(it.latitude, it.longitude)
                            } ?: run {
                                Toast.makeText(context, "Null location received", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("MyLocation", "Error fetching location", e)
                        }
                    }

                } else {
                    requestPermissions()
                }

            } else {
                enableLocation()
            }
        }
    }

    private fun getCityName(latitude: Double, longitude: Double) {
        try {
            // Check if network is available before attempting to use Geocoder
            if (isNetworkAvailable()) {
                val geoCoder = Geocoder(context, Locale.getDefault())
                val address = geoCoder.getFromLocation(latitude, longitude, 1)

                if (address != null && address.isNotEmpty()) {
                    val fulladdress = address[0].getAddressLine(0) ?: address[0].subAdminArea
                    val blockOrsector = address[0].subLocality ?: address[0].subAdminArea

                    // Save the address in SharedPreferences
                    context.getSharedPreferences("user_loc", MODE_PRIVATE)
                        .edit()
                        .putString("currentAddress", fulladdress)
                        .putString("blockOrSector", blockOrsector)
                        .apply()
                } else {
                    Log.e("MyLocation", "No address found for coordinates: $latitude, $longitude")
                }
            } else {
                Log.e("MyLocation", "Network unavailable, unable to perform geocoding")
            }
        } catch (e: Exception) {
            Log.e("MyLocation", "Unexpected error occurred", e)
        }
    }

    // function to request permissions
    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }


    // function to check if permissions are granted
    fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    // function to check if location is enabled
    fun isLocationEnabled(): Boolean {
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
                val intent = Intent(context, Notification::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
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
            Log.d("PermissionDenied", "Cancel button clicked, redirecting to LoginActivity")

            dialog.dismiss()

        }
        builder.create().show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    companion object {
        val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }
}