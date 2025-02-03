package com.trainee.project.swiggy.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.location.AddLocation
import com.trainee.project.swiggy.location.MyLocation
import com.trainee.project.swiggy.view.MainActivity

class LoginLaunchActivity : AppCompatActivity() {

    lateinit var loginBtn: AppCompatButton
    lateinit var mobileNumber: TextInputEditText
    lateinit var skipBtn: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myLocation: MyLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_launch_screen)
        myLocation = MyLocation(this)
        loginBtn = findViewById(R.id.loginBtn)
        mobileNumber = findViewById(R.id.mobileNumber)
        skipBtn = findViewById(R.id.skipBtn)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)

        // Check if login first time
        val logout = getSharedPreferences("logOut", MODE_PRIVATE)
        val isFirstTime = logout.getBoolean("isFirstTime", false)

        // If it the user first time true redirect to MainActivity
        if (isFirstTime) {
            var int = Intent(this, MainActivity::class.java)
            startActivity(int)
            finish()
        }

//        Login button click
        loginBtn.setOnClickListener {
            val number = mobileNumber.text.toString().trim()

            // Validate the phone number length
            if (number.length == 10) {

                val intent = Intent(this@LoginLaunchActivity, OtpVerificationActivity::class.java)
                intent.putExtra("phone", number)
                startActivity(intent)
                finish()
            } else {
                mobileNumber.error = "Please enter a valid 10-digit phone number"
            }
        }


        skipBtn.setOnClickListener {

            // Clear any saved user information
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // If location permissions are not granted ask user to add location
            if (!myLocation.checkPermissions()) {

                val intent = Intent(this@LoginLaunchActivity, AddLocation::class.java)
                startActivity(intent)
            }


        }
    }


}
