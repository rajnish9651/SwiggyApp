package com.trainee.project.swiggy.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.SharedPrefernces.PrefrenceKey
import com.trainee.project.swiggy.SharedPrefernces.SharedPreferencesManager
import com.trainee.project.swiggy.viewmodel.UserDetailsViewModel

class UserProfile : AppCompatActivity() {
    lateinit var backBtn: ImageView
    lateinit var number: TextView
    lateinit var userViewModel: UserDetailsViewModel
    lateinit var userName: TextInputEditText
    lateinit var userEmail: TextInputEditText
    lateinit var userMobile: TextInputEditText
    lateinit var nameSection: LinearLayout
    lateinit var emailSection: LinearLayout
    lateinit var numberSection: LinearLayout
    lateinit var updateName: AppCompatButton
    lateinit var nameCancel: AppCompatButton
    lateinit var updateEmail: AppCompatButton
    lateinit var cancelEmail: AppCompatButton

    private var initialUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        backBtn = findViewById(R.id.backArrow)
        number = findViewById(R.id.number)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        userMobile = findViewById(R.id.userMobile)
        nameSection = findViewById(R.id.nameSection)
        emailSection = findViewById(R.id.emailSection)
        numberSection = findViewById(R.id.numberSection)
        updateName = findViewById(R.id.updateName)
        nameCancel = findViewById(R.id.nameCancel)
        updateEmail = findViewById(R.id.updateEmail)
        cancelEmail = findViewById(R.id.cancelEmail)

        userViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]


        // Get phone number from SharedPreferences
        var sharedPreferencesManager=SharedPreferencesManager.getInstance(this) // Initialize the SharedPreferencesManager

        val phoneNumber = sharedPreferencesManager.getPhoneNumber(PrefrenceKey.USER_PHONE)
        number.text = phoneNumber

        if (phoneNumber != null) {
            userViewModel.getUserById(phoneNumber).observe(this, Observer { user ->
                if (user != null) {
                    Log.d("datauser", user.name)

                    initialUserName = user.name
                    // Set user details
                    userName.setText(user.name)
                    userEmail.setText(user.email)
                    userMobile.setText(user.number)

                    // Add TextWatchers AFTER setting text to avoid unnecessary triggers
                    addTextWatcher(userName, nameSection, user.name)
                    addTextWatcher(userEmail, emailSection, user.email)
//                    addTextWatcher(userMobile, numberSection,user.number)

                }
            })
        }

        backBtn.setOnClickListener {
            finish()
        }

        updateName.setOnClickListener {
            // Extract updated name from EditText
            val updatedName = userName.text.toString()
            userViewModel.updateUserName(phoneNumber!!, updatedName)
           recreate()

            userViewModel.getUserById(phoneNumber).observe(this, Observer { isUpdated ->
                if (isUpdated!=null) {
                    initialUserName = updatedName
                    userName.setText(updatedName)
                    nameSection.visibility = View.GONE
                }
            })


        }

        nameCancel.setOnClickListener {
            nameSection.visibility = View.GONE
        }
        updateEmail.setOnClickListener {
            // Extract updated name from EditText
            val updatedEmail = userEmail.text.toString()
            nameSection.visibility = View.GONE
            userViewModel.updateUserEmail(phoneNumber!!, updatedEmail)
            recreate()
        }

        cancelEmail.setOnClickListener {
            emailSection.visibility = View.GONE
        }


    }

    private fun addTextWatcher(editText: TextInputEditText, section: LinearLayout, initialText: String) {
        editText.addTextChangedListener(object : TextWatcher {
            private var baseText: String = initialText

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s?.toString() ?: ""

                // Show section only if the text has changed (not just a length change)
                if (newText != baseText) {
                    section.visibility = View.VISIBLE

                } else {
                    section.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
