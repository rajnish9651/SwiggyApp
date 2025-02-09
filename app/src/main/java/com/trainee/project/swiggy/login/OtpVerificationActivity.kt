package com.trainee.project.swiggy.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chaos.view.PinView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.SharedPrefernces.PrefrenceKey
import com.trainee.project.swiggy.SharedPrefernces.SharedPreferencesManager
import com.trainee.project.swiggy.location.AddLocation
import com.trainee.project.swiggy.location.MyLocation
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData
import com.trainee.project.swiggy.view.MainActivity
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class OtpVerificationActivity : AppCompatActivity() {

    lateinit var pinView: PinView
    lateinit var userViewModel: UserDetailsViewModel
    lateinit var inputTextNumber: TextView
    lateinit var myLocation: MyLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_verificataion_screen)

        // Get the phone number from the intent
        val number = intent.getStringExtra("phone")

        // Initialize the ViewModel
        userViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]

        pinView = findViewById(R.id.pinview)
        inputTextNumber = findViewById(R.id.inputTextNumber)
        inputTextNumber.text = "Send to +91 ${number}"

        myLocation = MyLocation(this)
        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {  // Check if the pin is of length 6
                if (p0?.length == 6 && (p0.toString() == "111111")) {
                    // First, check if the phone number exists in the database
                    number?.let {

                        userViewModel.isPhoneNumberExists(it).observe(this@OtpVerificationActivity) { exists ->
                            if (exists) {

//                                SharedPreferencesManager.savePhoneNumber(PrefrenceKey.USER_PHONE,it)
                                var sharedPreferencesManager=SharedPreferencesManager.getInstance(this@OtpVerificationActivity) // Initialize the SharedPreferencesManager

                           sharedPreferencesManager.savePhoneNumber(PrefrenceKey.USER_PHONE,it)
                                finish()

                                if (myLocation.checkPermissions()) {
                                    val intent = Intent(this@OtpVerificationActivity, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this@OtpVerificationActivity, AddLocation::class.java)
                                    startActivity(intent)
                                }

                            }
                            else {

                                userViewModel.insertUserDetailsViewModel(UserDeatailsData(0, it, "", ""))

                                Log.d("dataaaa" ,it.toString())
                            }

                        }

                    }
                }
                else{
                    pinView.error = "Invalid OTP. Please try again."
                }
            }
        })
    }
}

