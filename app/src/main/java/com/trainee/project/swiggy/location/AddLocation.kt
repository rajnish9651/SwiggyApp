package com.trainee.project.swiggy.location

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trainee.project.swiggy.R

class AddLocation : AppCompatActivity() {

    lateinit var backBtn: ImageView
    lateinit var addNewAddress: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_location)

        backBtn = findViewById(R.id.backBtn)
        addNewAddress = findViewById(R.id.addNewAddress)


        backBtn.setOnClickListener {
            finish()
        }

        addNewAddress.setOnClickListener {
            val intent=Intent(this,MapActivity::class.java)
            startActivity(intent)
        }

    }
}