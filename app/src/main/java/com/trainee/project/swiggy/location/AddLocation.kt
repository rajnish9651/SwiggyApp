package com.trainee.project.swiggy.location

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.adapter.SavedLoactionAdapter
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData
import com.trainee.project.swiggy.view.MainActivity
import com.trainee.project.swiggy.viewmodel.UserSavedLocationViewModel

class AddLocation : AppCompatActivity() {


    lateinit var backBtnAddLocation: ImageView
    lateinit var addressRecylerview: RecyclerView
    lateinit var locationAdd: RelativeLayout
    lateinit var currentLocation: RelativeLayout


    lateinit var userViewModel: UserSavedLocationViewModel
    lateinit var savedLoactionAdapter: SavedLoactionAdapter


    lateinit var loactionList: MutableList<UserSavedLocationData>
    lateinit var myLocation: MyLocation

    // onCreate function for initial setup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve shared preferences to get user phone number
        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)

        setContentView(R.layout.activity_add_location)

        backBtnAddLocation = findViewById(R.id.backBtnAddLocation)
        locationAdd = findViewById(R.id.locationAdd)
        currentLocation = findViewById(R.id.currentLocation)
        addressRecylerview = findViewById(R.id.addressRecylerview)

        // Initialize the location list and MyLocation instance
        loactionList = mutableListOf()
        myLocation = MyLocation(this)

        // Initialize the ViewModel
        userViewModel = ViewModelProvider(this)[UserSavedLocationViewModel::class.java]

        // If the user phone number is not available, hide the add location button
        if (phoneNumber == null) {
            locationAdd.visibility = View.GONE
        } else {
            // Set the flag for first-time user
            getSharedPreferences("logOut", MODE_PRIVATE).edit()
                .putBoolean("isFirstTime", true)
                .apply()
        }

        // Get the current location of the device
        myLocation.getCurrentLocation()



        // If phone number exists, fetch the saved addresses from the ViewModel
        if (phoneNumber != null) {
            userViewModel.getUserAddressByNumber(phoneNumber)
                .observe(this, Observer { addressList ->
                    // If address list is not null, add the addresses to the list
                    if (addressList != null) {
                        loactionList.addAll(addressList)
                    }
                })
        }

        // Set up the adapter and RecyclerView for displaying saved locations
        savedLoactionAdapter = SavedLoactionAdapter(loactionList)
        addressRecylerview.layoutManager = LinearLayoutManager(this)
        addressRecylerview.adapter = savedLoactionAdapter

        // Back button click listener to finish the activity
        backBtnAddLocation.setOnClickListener {
            Log.d("AddLocation", "Back button clicked")
            finish()
        }

        // Location Add button to navigate to MapActivity for adding a new address
        locationAdd.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        // Current Location button to check for location permission and navigate to MapActivity
        currentLocation.setOnClickListener {
            if (myLocation.checkPermissions() && myLocation.isLocationEnabled()) {
                // If permissions are granted and location is enabled, navigate to MapActivity
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            } else {
                // If permissions are not granted, show a toast and request permissions
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
                myLocation.requestPermissions()
            }
        }
    }

    // Handle location permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        myLocation.onRequestPermissionsResultMyLocation(requestCode, permissions, grantResults)
    }
}
