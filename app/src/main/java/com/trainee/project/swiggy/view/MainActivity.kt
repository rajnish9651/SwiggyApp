package com.trainee.project.swiggy.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.location.AddLocation
import com.trainee.project.swiggy.location.MyLocation
import com.trainee.project.swiggy.login.LoginActivity
import com.trainee.project.swiggy.profile.UserDeatails

class MainActivity : AppCompatActivity() {


    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var profileBackground: LinearLayout
    lateinit var profileImg: ImageView
    lateinit var addLocations: LinearLayout
    private lateinit var myLocation: MyLocation
    lateinit var current_location: TextView
    lateinit var location_Head: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        bottomNavigationView = findViewById(R.id.bottom_nav)
        profileBackground = findViewById(R.id.top_container_layout)
        profileImg = findViewById(R.id.profileImg)
        addLocations = findViewById(R.id.addLocations)
        current_location = findViewById(R.id.current_location)
        location_Head = findViewById(R.id.location_Head)

        myLocation = MyLocation(this) // Initialize location service

        // Load default fragment (HomeScreen)
        loadFragment(HomeScreen())

        // Bottom navigation view item selected listener
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.food -> {
                    loadFragment(FoodFragment())
                    profileBackground.setBackgroundColor(Color.parseColor("#315DB9"))
                    true
                }

                R.id.instamart -> {
                    loadFragment(InstamartFragment())
                    true
                }

                R.id.dineout -> {
                    loadFragment(DineoutFragment())
                    profileBackground.setBackgroundColor(Color.parseColor("#FFE2AE"))
                    true
                }

                R.id.reorder -> {
                    loadFragment(GenieFragment())
                    profileBackground.setBackgroundColor(Color.parseColor("#E3DAFF"))
                    true
                }

                R.id.swiggy -> {
                    loadFragment(HomeScreen())
                    profileBackground.setBackgroundColor(Color.parseColor("#ffffff"))
                    true
                }

                else -> false
            }
        }

        // SharedPreferences to get user phone number
        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)

        // Get the current location of the device
        myLocation.getCurrentLocation()

        // Retrieve location details from SharedPreferences
        val sharedPreferences2 = getSharedPreferences("user_loc", MODE_PRIVATE)
        val currentAddress = sharedPreferences2.getString("currentAddress", null)
        val blockOrSector = sharedPreferences2.getString("blockOrSector", null)

        // Update UI with current location and sector/block
        current_location.text = currentAddress
        location_Head.text = blockOrSector

        // Mark the first time so that LoginLaunch Activity not show
        getSharedPreferences("logOut", MODE_PRIVATE).edit()
            .putBoolean("isFirstTime", true)
            .apply()

        // Handle profile image click to navigate to User Details or Login Activity
        profileImg.setOnClickListener {
//            Log.d("checkphone", phoneNumber.toString())
            if (phoneNumber != null) {

                val intent = Intent(this@MainActivity, UserDeatails::class.java)
                startActivity(intent)
            } else {

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        // Click to navigate to AddLocation activity
        addLocations.setOnClickListener {
            val intent = Intent(this@MainActivity, AddLocation::class.java)
            startActivity(intent)
        }
    }

    // Function to load a fragment into the container
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragment, fragment)
        transaction.commit()
    }

    //  back button press behavior
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()  // Close all activities in the stack
    }

    // Handle location permissions result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        myLocation.onRequestPermissionsResultMyLocation(requestCode, permissions, grantResults)
    }

    // onRestart is called when the activity is restarted
    override fun onRestart() {
        super.onRestart()

        // Check if location permission is granted
        if (myLocation.checkPermissions()) {
            // If permission is granted, get current location
            myLocation.getCurrentLocation()

            // Update the UI directly with current location from SharedPreferences
            val sharedPreferences = getSharedPreferences("user_loc", MODE_PRIVATE)
            val currentAddress = sharedPreferences.getString("currentAddress", null)
            val blockOrSector = sharedPreferences.getString("blockOrSector", null)

            // Update location UI components
            current_location.text = currentAddress
            location_Head.text = blockOrSector
        }
    }
}
