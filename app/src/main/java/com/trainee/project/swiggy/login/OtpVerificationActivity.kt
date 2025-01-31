package com.trainee.project.swiggy.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chaos.view.PinView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.notification.Notification
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class OtpVerificationActivity : AppCompatActivity() {

    lateinit var pinView: PinView
    lateinit var userViewModel: UserDetailsViewModel
    lateinit var inputTextNumber: TextView

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

        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Check if the pin is of length 6
                if (p0?.length == 6 && (p0.toString() == "111111")) {
                    // First, check if the phone number exists in the database
                    number?.let {

                        userViewModel.isPhoneNumberExists(it).observe(this@OtpVerificationActivity) { exists ->
                            if (exists) {

                                    val intent = Intent(this@OtpVerificationActivity, Notification::class.java)
                                    startActivity(intent)
                                    // Saving the phone number to SharedPreferences
                                    val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putString("user_phone", it)
                                    editor.apply()
                                    finish()
                                }
                            else {

                                userViewModel.insertUserDetailsViewModel(UserDeatailsData(0, it, "", ""))

                            }

                        }

                    }
                }
                else{
                    pinView.error = "Invalid OTP. Please try again."
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}

