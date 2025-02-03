package com.trainee.project.swiggy.location

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.adapter.SavedAddressProfileAdapter
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData
import com.trainee.project.swiggy.viewmodel.UserSavedLocationViewModel

class SavedAdresssActivity : AppCompatActivity(), SavedProfileAddressListener {
    private lateinit var showRecyclerViewSaved: RecyclerView
    private lateinit var userViewModel: UserSavedLocationViewModel
    private lateinit var savedLocationAdapter: SavedAddressProfileAdapter
    private val locationList = mutableListOf<UserSavedLocationData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saved_adresss)

        showRecyclerViewSaved = findViewById(R.id.showRecyclerViewSaved)

        userViewModel = ViewModelProvider(this)[UserSavedLocationViewModel::class.java]

        // Retrieve shared preferences to get user phone number
        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)

        // If phone number exists, fetch the saved addresses from the ViewModel
        if (phoneNumber != null) {
            userViewModel.getUserAddressByNumber(phoneNumber)
                .observe(this, Observer { addressList ->
                    // If address list is not null, add the addresses to the list
                    if (addressList != null) {
                        locationList.addAll(addressList)
                    }
                })
        }
        savedLocationAdapter = SavedAddressProfileAdapter(locationList, this)
        showRecyclerViewSaved.layoutManager = LinearLayoutManager(this)
        showRecyclerViewSaved.adapter = savedLocationAdapter


    }


    override fun deleteSavedAddrress(id: Int, phone: String) {
//        userViewModel.deleteAddressById(id,phone)
    }

    override fun editSavedAddrress(id: Int, address: String) {

    }
}
