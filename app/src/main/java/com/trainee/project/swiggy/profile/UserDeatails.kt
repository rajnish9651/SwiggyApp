package com.trainee.project.swiggy.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.SharedPrefernces.PrefrenceKey
import com.trainee.project.swiggy.SharedPrefernces.SharedPreferencesManager
import com.trainee.project.swiggy.cart.AddToCart
import com.trainee.project.swiggy.location.SavedAdresssActivity
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
    lateinit var savedAdress: RelativeLayout


    lateinit var backBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        var sharedPreferencesManager=SharedPreferencesManager.getInstance(this)

        val phoneNumber = sharedPreferencesManager.getPhoneNumber(PrefrenceKey.USER_PHONE)
        if (phoneNumber == null) {
            // If no phone number found in SharedPreferences, redirect to login screen
            val intent = Intent(this, LoginLaunchActivity::class.java)
            startActivity(intent)
            finish()

        }
        setContentView(R.layout.profile)

        userEatlists = findViewById(R.id.userEatlists)
        backBtn = findViewById(R.id.backBtn)
        editProfile = findViewById(R.id.editProfile)
        logOut = findViewById(R.id.logOut)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        userNumber = findViewById(R.id.userNumber)
        savedAdress = findViewById(R.id.savedAdress)


        userViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]


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

//                SharedPreferencesManager.clearSharedPrefernce()
                sharedPreferencesManager.clearSharedPrefernce()

                // Mark the first time so that LoginLaunch Activity not show
                sharedPreferencesManager.saveLoginStatus(PrefrenceKey.LOGIN_LOGOUT_STATUS,false)

                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("logout","out").apply()
                var intent = Intent(this@UserDeatails, LoginLaunchActivity::class.java)
//                intent.putExtra("logout","out")

                startActivity(intent)
                finish()


            }

        }


        savedAdress.setOnClickListener {
            var intent=Intent(this@UserDeatails,SavedAdresssActivity::class.java)
            startActivity(intent)
        }

    }
}