package com.trainee.project.swiggy.location

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.adapter.SavedLoactionAdapter
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData
import com.trainee.project.swiggy.viewmodel.UserSavedLocationViewModel

class AddLocation : AppCompatActivity() {

    lateinit var backBtnAddLocation: ImageView


    lateinit var addressRecylerview: RecyclerView
    lateinit var userViewModel: UserSavedLocationViewModel
    lateinit var savedLoactionAdapter: SavedLoactionAdapter
    lateinit var loactionList: MutableList<UserSavedLocationData>
    lateinit var locationAdd: RelativeLayout
    lateinit var currentLocation: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)
        setContentView(R.layout.activity_add_location)

        backBtnAddLocation = findViewById(R.id.backBtnAddLocation)
//        addNewAddress = findViewById(R.id.addNewAddress)
        locationAdd = findViewById(R.id.locationAdd)
        currentLocation = findViewById(R.id.currentLocation)
        addressRecylerview = findViewById(R.id.addressRecylerview)
        loactionList = mutableListOf()

        if (phoneNumber == null) {
            locationAdd.visibility = View.GONE

        }

        userViewModel = ViewModelProvider(this)[UserSavedLocationViewModel::class.java]


        if (phoneNumber != null) {
            userViewModel.getUserAddressByNumber(phoneNumber)
                .observe(this, Observer { addressList ->
                    if (addressList != null) {

                        loactionList.addAll(addressList)
                    }
                })
        }
        savedLoactionAdapter = SavedLoactionAdapter(loactionList)
        addressRecylerview.layoutManager = LinearLayoutManager(this)
        addressRecylerview.adapter = savedLoactionAdapter


        backBtnAddLocation.setOnClickListener {
            Log.d("AddLocation", "Back button clicked")
            finish()
        }

        locationAdd.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        currentLocation.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }
}