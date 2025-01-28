package com.trainee.project.swiggy.location

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.trainee.project.swiggy.R

class AddLocation : AppCompatActivity() {

    lateinit var backBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_location)

        backBtn = findViewById(R.id.backBtn)


        backBtn.setOnClickListener {
            finish()
        }

    }
}