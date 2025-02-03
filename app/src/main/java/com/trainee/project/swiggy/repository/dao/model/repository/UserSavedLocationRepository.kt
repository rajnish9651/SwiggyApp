package com.trainee.project.swiggy.repository.dao.model.repository

import androidx.lifecycle.LiveData
import com.trainee.project.swiggy.repository.dao.model.dao.UserDao
import com.trainee.project.swiggy.repository.dao.model.dao.UserSavedLocationDao
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData

class UserSavedLocationRepository(private val userSavedLocationDao: UserSavedLocationDao) {

    suspend fun saveUserLocation(userLocation: UserSavedLocationData) {
//        val userLocation = UserLocation(userId = userId, address = address, city = city)
        userSavedLocationDao.insert(userLocation)
    }

    suspend fun getUserLocations(userId: String): List<UserSavedLocationData> {
        return userSavedLocationDao.getLocationsByUser(userId)
    }


    // Function to get user address by phone number
     fun getUserLocationByPhoneNumber(phoneNumber: String): LiveData<List<UserSavedLocationData>> {
        return userSavedLocationDao.getUserLocationByPhoneNumber(phoneNumber)
    }

   suspend fun deleteAddressByIdRepo(id: Int) {

       userSavedLocationDao.deleteAddressByIdDao(id)

    }

     suspend fun getUserAddressRepo(phoneNumber:String): List<UserSavedLocationData>{
       return userSavedLocationDao.getUserAddressDao(phoneNumber)
    }
}