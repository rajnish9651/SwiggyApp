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
import com.trainee.project.swiggy.profile.UserDeatails


class HomeScreen : Fragment(){

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

        // Inflate the search bar layout
        val searchBarView = inflater.inflate(R.layout.search_bar, container, false)


        foodDelivery = view.findViewById(R.id.card_food)
        instamart = view.findViewById(R.id.instamart)
        dineout = view.findViewById(R.id.dineout)




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



}