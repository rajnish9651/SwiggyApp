package com.trainee.project.swiggy.notification

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.NotificationManagerCompat
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.view.MainActivity


class Notification : AppCompatActivity() {

    lateinit var btnTurnOn: AppCompatButton
    lateinit var btnNot: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.notification_screen)
        btnTurnOn = findViewById(R.id.btnTurnOn)
        btnNot = findViewById(R.id.btnNot)


        if (areNotificationsEnabled()) {
            val intent = Intent(this@Notification, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnTurnOn.setOnClickListener {
            if (!areNotificationsEnabled()) {
                redirectToNotificationSettings();
            } else {
                val intent = Intent(this@Notification, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
        }


        btnNot.setOnClickListener {
            val intent = Intent(this@Notification, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(this).areNotificationsEnabled()
    }

    private fun redirectToNotificationSettings() {
        val intent: Intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        } else {

            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:$packageName"))
        }

        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        if (!areNotificationsEnabled()) {
            redirectToNotificationSettings();
        } else {

        }

    }
}