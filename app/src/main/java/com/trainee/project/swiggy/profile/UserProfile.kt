package com.trainee.project.swiggy.profile

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class UserProfile : AppCompatActivity() {
    lateinit var backBtn: ImageView
    lateinit var number: TextView
    lateinit var userViewModel: UserDetailsViewModel
    lateinit var userName: TextInputEditText
    lateinit var userEmail: TextInputEditText
    lateinit var userMobile: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        backBtn = findViewById(R.id.backArrow)
        number = findViewById(R.id.number)
        userName = findViewById(R.id.userName)
        userViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]

        //  the phone number from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        var phoneNumber = sharedPreferences.getString("user_phone", null)
        number.text = phoneNumber
        if (phoneNumber != null) {
            // bbserve the LiveData from ViewModel to get the user details
            userViewModel.getUserById(phoneNumber).observe(this, Observer { user ->
                if (user != null) {

                    Log.d("datauser", user.name)

                    // update the TextView with the user's phone number

                    userName.setText(user.name)
                }
            })
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
