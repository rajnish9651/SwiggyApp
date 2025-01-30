package com.trainee.project.swiggy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.trainee.project.swiggy.repository.dao.model.DBHelper
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData
import com.trainee.project.swiggy.repository.dao.model.repository.UserSavedLocationRepository
import kotlinx.coroutines.launch

class UserSavedLocationViewModel(application: Application) : AndroidViewModel(application) {


    val dbHelperInstance = DBHelper.getDatabase(application)
    val userSavedLocationRepo = UserSavedLocationRepository(dbHelperInstance.userSavedLocationDao())


    fun saveLocationToDb(userLocation: UserSavedLocationData) {
        viewModelScope.launch {
            userSavedLocationRepo.saveUserLocation(userLocation)
        }
    }

    // Function to get user address by phone number
    fun getUserAddressByNumber(phoneNumber: String): LiveData<List<UserSavedLocationData>> {
        // Fetch the data from the repository and return it as LiveData

        return userSavedLocationRepo.getUserLocationByPhoneNumber(phoneNumber)
    }

}