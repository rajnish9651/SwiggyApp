package com.trainee.project.swiggy.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.view.MainActivity

class SplaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splace)

        Handler(Looper.getMainLooper()).postDelayed({
            val logout = getSharedPreferences("logOut", MODE_PRIVATE)
            val isFirstTime = logout.getBoolean("isFirstTime", false)

            if (!isFirstTime) {
                val intent = Intent(this, LoginLaunchActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}