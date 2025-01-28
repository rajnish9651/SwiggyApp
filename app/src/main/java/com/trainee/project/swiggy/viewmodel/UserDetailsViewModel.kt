package com.trainee.project.swiggy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.trainee.project.swiggy.repository.dao.model.DBHelper
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData
import com.trainee.project.swiggy.repository.dao.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val dbHelperInstance1 = DBHelper.getDatabase(application)
    val userrepository = UserRepository(dbHelperInstance1.userDao())

//    private val _allCartItemsMutable = MutableLiveData<List<FoodTypeData>>()
//    val allCartItems: LiveData<List<FoodTypeData>> = _allCartItemsMutable


    fun insertUserDetailsViewModel(userData: UserDeatailsData) {

        viewModelScope.launch(Dispatchers.IO) {
            userrepository.insertUserDataRepo(userData)

        }
    }

    // Function to check if the phone number exists
    fun isPhoneNumberExists(phoneNumber: String): LiveData<Boolean> {
        return userrepository.isPhoneNumberExists(phoneNumber)
    }



    // Fetch user details by phone number (returning LiveData)
    fun getUserById(number: String): LiveData<UserDeatailsData> {
        return userrepository.getUserById(number)
    }
}