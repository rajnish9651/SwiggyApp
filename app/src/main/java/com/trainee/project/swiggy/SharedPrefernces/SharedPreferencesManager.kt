package com.trainee.project.swiggy.SharedPrefernces

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCE_NAME = "user_info"
        @Volatile
        private var INSTANCE: SharedPreferencesManager? = null
        fun getInstance(context: Context): SharedPreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferencesManager(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    fun savePhoneNumber(key:String,value:String){
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getPhoneNumber(key:String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveLoginStatus(key:String,value:   Boolean=false){
        sharedPreferences.edit().putBoolean(key,value).apply()
    }


    fun getLoginStatus(key:String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearSharedPrefernce(){
        sharedPreferences.edit().clear().apply()
    }


}