package com.trainee.project.swiggy.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.location.AddLocation
import com.trainee.project.swiggy.profile.UserDeatails

class MainActivity : AppCompatActivity() {


    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var profileBackground: LinearLayout
    lateinit var profileImg: ImageView
    lateinit var addLocations: LinearLayout
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


        profileImg.setOnClickListener {
            val intent = Intent(this@MainActivity, UserDeatails::class.java)
            startActivity(intent)
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
}