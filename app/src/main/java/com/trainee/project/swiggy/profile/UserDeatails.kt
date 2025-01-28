package com.trainee.project.swiggy.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.cart.AddToCart
import com.trainee.project.swiggy.login.LoginLaunchActivity
import com.trainee.project.swiggy.view.MainActivity

class UserDeatails : AppCompatActivity() {


    lateinit var userEatlists:TextView
    lateinit var editProfile:TextView
    lateinit var logOut:TextView

    lateinit var backBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile)

        userEatlists=findViewById(R.id.userEatlists)
        backBtn=findViewById(R.id.backBtn)
        editProfile=findViewById(R.id.editProfile)
        logOut=findViewById(R.id.logOut)

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

            var intent=Intent(this@UserDeatails,LoginLaunchActivity::class.java)
            startActivity(intent)
            finish()


        }

    }
}