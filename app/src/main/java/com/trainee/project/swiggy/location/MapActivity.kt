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
import com.trainee.project.swiggy.view.MainActivity
import java.util.Locale

class MapActivity : AppCompatActivity(), OnMapReadyCallback,SavedAddressClickListner {

    private val FINE_PERMISSION_CODE = 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var myMap: GoogleMap
    private lateinit var mySearchView: EditText
    private lateinit var locationText: TextView
    private lateinit var currentLocationBtn: LinearLayout
    private lateinit var adressTitleText: TextView
    private lateinit var confirmLocationBtn: CardView

    private var selectedAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mySearchView = findViewById(R.id.locationSearchView)
        locationText = findViewById(R.id.locationText)
        currentLocationBtn = findViewById(R.id.currentLocationBtn)
        adressTitleText = findViewById(R.id.adressTitleText)
        confirmLocationBtn = findViewById(R.id.confirmLocationBtn)

        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                searchLocation(p0.toString())

            }
        })

        currentLocationBtn.setOnClickListener { getLastLocation() }

        getLastLocation()

        confirmLocationBtn.setOnClickListener {

            if (selectedAddress!=null && phoneNumber!=null){


            var bottomsheet = LoctionDetalisBottomSheet()
            var bundle = Bundle()
            bundle.putString("address", selectedAddress)
            bundle.putString("phoneNumber", phoneNumber)
            Log.d("Addd",phoneNumber.toString())
            bottomsheet.arguments = bundle
            bottomsheet.show(supportFragmentManager, "bottomsheet")

            }

        }
    }

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

    private fun getLastLocation() {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_PERMISSION_CODE
            )
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
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

    private fun updateLocationUI(latLng: LatLng, title: String) {
        myMap.clear()
        myMap.addMarker(MarkerOptions().position(latLng).title(title))
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        val geocoder = Geocoder(this, Locale.getDefault())

        val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        locationText.text =
            "Current Location: ${address?.firstOrNull()?.getAddressLine(0) ?: "$latLng"}"
        adressTitleText.text = "Current City: ${address?.get(0)?.locality ?: "$latLng"}"

        selectedAddress = address?.firstOrNull()?.getAddressLine(0);

    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

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

    override fun onSavedAddressConfirmed() {
        // Handle the address saved confirmation and navigate if needed
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}
