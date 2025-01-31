package com.trainee.project.swiggy.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeScreen())
        bottomNavigationView = findViewById(R.id.bottom_nav)
        profileBackground = findViewById(R.id.top_container_layout)
        profileImg = findViewById(R.id.profileImg)
        addLocations = findViewById(R.id.addLocations)

//        searchBar = findViewById(com.hbb20.R.id.search_bar)
        myLocation = MyLocation(this)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.food -> {
                    loadFragment(FoodFragment())

                    profileBackground.setBackgroundColor(Color.parseColor("#315DB9"))
//                    searchBar.setBackgroundColor(Color.parseColor("#315DB9"))
                    true
                }

                R.id.instamart -> {
                    loadFragment(InstamartFragment())
                    true
                }

                R.id.dineout -> {
                    loadFragment(DineoutFragment())//
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

                else -> false//E3DAFF
            }
        }


        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)

        val phoneNumber = sharedPreferences.getString("user_phone", null)

        // Get the current location of the device
        if (isInternetAvailable()) {
            myLocation.getCurrentLocation()
        } else {

            Toast.makeText(this, "No intenet connection", Toast.LENGTH_SHORT).show()
            showNoInternetDialogBox()
        }

        getSharedPreferences("logOut", MODE_PRIVATE).edit()
            .putBoolean("isFirstTime", true)
            .apply()


        profileImg.setOnClickListener {

            if (phoneNumber!=null){
                val intent = Intent(this@MainActivity, UserDeatails::class.java)
                startActivity(intent)
//
            }else{
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)

            }

        }

        addLocations.setOnClickListener {
            val intent = Intent(this@MainActivity, AddLocation::class.java)
            startActivity(intent)
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

    }

    fun showNoInternetDialogBox() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("your internet is Off. please On it")
        builder.setTitle("No internet connection")
        builder.setCancelable(false)
        builder.setPositiveButton("On") { diallog, which ->
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,

        ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        myLocation.onRequestPermissionsResultMyLocation(requestCode, permissions, grantResults)
    }




}