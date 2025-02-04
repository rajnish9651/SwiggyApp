package com.trainee.project.swiggy.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.view.MainActivity
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn: AppCompatButton
    lateinit var mobileNumber: TextInputEditText
    lateinit var skipBtn: TextView

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
//
//        loginBtn = findViewById(R.id.loginBtn)
//        mobileNumber = findViewById(R.id.mobileNumber)
//        skipBtn = findViewById(R.id.skipBtn)
//
//        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
//
//        loginBtn.setOnClickListener {
//            val number = mobileNumber.text.toString().trim()
//
//            if (number.length == 10) {
//                // Start OtpVerificationActivity and pass the phone number
//                val intent = Intent(this@LoginActivity, OtpVerificationActivity::class.java)
//                intent.putExtra("phone", number)
//                startActivity(intent)
//                finish()
//            } else {
//                mobileNumber.error = "Please enter a valid 10-digit phone number"
//            }
//        }

//        skipBtn.setOnClickListener {
//            // Clear the shared preferences
//            val editor = sharedPreferences.edit()
//            editor.clear()
//            editor.apply()
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }


}
