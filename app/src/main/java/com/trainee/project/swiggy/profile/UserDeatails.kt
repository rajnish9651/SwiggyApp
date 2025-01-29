package com.trainee.project.swiggy.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.cart.AddToCart
import com.trainee.project.swiggy.login.LoginLaunchActivity
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class UserDeatails : AppCompatActivity() {


    lateinit var userEatlists: TextView
    lateinit var editProfile: TextView
    lateinit var logOut: TextView
    lateinit var userName: TextView
    lateinit var userEmail: TextView
    lateinit var userNumber: TextView
    lateinit var userViewModel: UserDetailsViewModel


    lateinit var backBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile)

        userEatlists = findViewById(R.id.userEatlists)
        backBtn = findViewById(R.id.backBtn)
        editProfile = findViewById(R.id.editProfile)
        logOut = findViewById(R.id.logOut)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        userNumber = findViewById(R.id.userNumber)


        userViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]


        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)


        if (phoneNumber != null) {
            userViewModel.getUserById(phoneNumber).observe(this, Observer { user ->

                if (user != null) {
                    Log.d("datauser", user.name)
                    userName.text = user.name
                    userEmail.text = user.email
                    userNumber.text = user.number

                }


            })


            userEatlists.setOnClickListener {

                val intent = Intent(this, AddToCart::class.java)
                startActivity(intent)
            }

            backBtn.setOnClickListener {
                finish()
            }

            editProfile.setOnClickListener {
                val intent = Intent(this, UserProfile::class.java)
                startActivity(intent)
            }

            logOut.setOnClickListener {

                var intent = Intent(this@UserDeatails, LoginLaunchActivity::class.java)
                startActivity(intent)
                finish()

            }

        }
    }
}