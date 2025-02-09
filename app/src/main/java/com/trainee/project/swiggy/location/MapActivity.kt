package com.trainee.project.swiggy.location

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.SharedPrefernces.PrefrenceKey
import com.trainee.project.swiggy.SharedPrefernces.SharedPreferencesManager
import com.trainee.project.swiggy.view.MainActivity
import java.util.Locale

class MapActivity : AppCompatActivity(), OnMapReadyCallback, SavedAddressClickListner {

    // Request code for location permission
    private val FINE_PERMISSION_CODE = 1

    // Google Maps and Location Client
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var myMap: GoogleMap

    private lateinit var mySearchView: EditText
    private lateinit var locationText: TextView
    private lateinit var currentLocationBtn: LinearLayout
    private lateinit var adressTitleText: TextView
    private lateinit var confirmLocationBtn: CardView

    // Variable to store the selected address
    private var selectedAddress: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mySearchView = findViewById(R.id.locationSearchView)
        locationText = findViewById(R.id.locationText)
        currentLocationBtn = findViewById(R.id.currentLocationBtn)
        adressTitleText = findViewById(R.id.adressTitleText)
        confirmLocationBtn = findViewById(R.id.confirmLocationBtn)

        // Get the user phone number from shared preferences

        var sharedPreferencesManager=SharedPreferencesManager.getInstance(this)

        val phoneNumber = sharedPreferencesManager.getPhoneNumber(PrefrenceKey.USER_PHONE)

        // Initialize the FusedLocationProviderClient to get location data
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Set up the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Text watcher to the search field for location search
        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                // Call location search when text changes
                searchLocation(p0.toString())
            }
        })

        // Current location button listener to fetch the user current location
        currentLocationBtn.setOnClickListener { getLastLocation() }

        // Fetch the current location on activity creation
        getLastLocation()

        // Confirm location button listener to handle selected address confirmation
        confirmLocationBtn.setOnClickListener {
            if (selectedAddress != null && phoneNumber != null) {
                // If the address is selected, show a bottom sheet with address details
                val bottomsheet = LoctionDetalisBottomSheet()
                val bundle = Bundle()
                bundle.putString("address", selectedAddress)
                bundle.putString("phoneNumber", phoneNumber)
                Log.d("Addd", phoneNumber.toString())
                bottomsheet.arguments = bundle
                bottomsheet.show(supportFragmentManager, "bottomsheet")
            }
        }
    }

    // Search for a location using the entered query
    private fun searchLocation(query: String?) {
        if (query.isNullOrEmpty()) return

        val geocoder = Geocoder(this)

        val addressList = geocoder.getFromLocationName(query, 1)
        addressList?.firstOrNull()?.let {
            val latLng = LatLng(it.latitude, it.longitude)
            myMap.clear()
            myMap.addMarker(MarkerOptions().position(latLng).title(query))
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            locationText.text = "Selected Location: ${it.getAddressLine(0) ?: query}"
            adressTitleText.text = "Selected Location: ${it.locality ?: it.featureName}"
        }
    }

    // Get the last known location of the user
    private fun getLastLocation() {
        if (!hasLocationPermission()) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_PERMISSION_CODE
            )
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                // Update the UI with the current location
                val latLng = LatLng(it.latitude, it.longitude)
                updateLocationUI(latLng, "Current Location")
            }
        }
    }


    private fun hasLocationPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    // Update the UI with the provided location
    private fun updateLocationUI(latLng: LatLng, title: String) {
        myMap.clear()
        myMap.addMarker(MarkerOptions().position(latLng).title(title))
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        locationText.text =
            "Current Location: ${address?.firstOrNull()?.getAddressLine(0) ?: "$latLng"}"
        adressTitleText.text = "Current City: ${address?.get(0)?.locality ?: "$latLng"}"

        selectedAddress = address?.firstOrNull()?.getAddressLine(0)
    }

    // Called when the map is ready
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        // Set a listener for map clicks to update the location UI
        myMap.setOnMapClickListener { latLng ->
            updateLocationUI(latLng, "Clicked Location")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FINE_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            getLastLocation()
        } else {
            // If permission is denied, show a dialog to guide the user to enable permissions
            showPermissionDialog()
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Location Permission Needed")
            .setMessage("This app requires location permission. Enable it in settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // saved address confirmation
    override fun onSavedAddressConfirmed() {
        // Navigate back to the main activity after confirming the address
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }


}
