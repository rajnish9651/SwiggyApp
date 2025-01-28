package com.trainee.project.swiggy.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.location.AddLocation
import com.trainee.project.swiggy.location.MyLocation
import com.trainee.project.swiggy.location.OnCityReceivedListener
import com.trainee.project.swiggy.profile.UserDeatails


class HomeScreen : Fragment(), OnCityReceivedListener {

    lateinit var current_location: TextView
    var currentLocation: String? = null
    lateinit var myLocation: MyLocation
    lateinit var userDeatails: ImageView
    lateinit var addLocation: LinearLayout
    lateinit var foodDelivery: RelativeLayout
    lateinit var instamart: RelativeLayout
    lateinit var dineout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        val view2 = inflater.inflate(R.layout.fragment_home_screen, container, false)

        // Inflate the search bar layout
        val searchBarView = inflater.inflate(R.layout.search_bar, container, false)

//        current_location = view.findViewById(R.id.current_location)
//        userDeatails = view.findViewById(R.id.profileImg)
//        addLocation = view.findViewById(R.id.addLocations)
        foodDelivery = view.findViewById(R.id.card_food)
        instamart = view.findViewById(R.id.instamart)
        dineout = view.findViewById(R.id.dineout)

        myLocation = activity?.let { MyLocation(it, this) }!!

        myLocation.getCurrentLocation()

//        userDeatails.setOnClickListener {
//
//            val intent = Intent(activity, UserDeatails::class.java)
//            startActivity(intent)
//
//        }

//        addLocation.setOnClickListener {
//            val intent = Intent(activity, AddLocation::class.java)
//            startActivity(intent)
//        }

        foodDelivery.setOnClickListener {
            (activity as? MainActivity)?.bottomNavigationView?.selectedItemId = R.id.food

        }

        instamart.setOnClickListener {
            (activity as? MainActivity)?.bottomNavigationView?.selectedItemId = R.id.instamart

        }

        dineout.setOnClickListener {
            (activity as? MainActivity)?.bottomNavigationView?.selectedItemId = R.id.dineout
        }

        Log.d("cityMain", currentLocation.toString())

        return view
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,

        ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        myLocation.onRequestPermissionsResultMyLocation(requestCode, permissions, grantResults)
    }

    override fun onCityReceived(city: String) {
//        currentLocation = city
//        current_location.text = currentLocation
        Log.d("HomeScreen", "City received: $currentLocation")
    }




}