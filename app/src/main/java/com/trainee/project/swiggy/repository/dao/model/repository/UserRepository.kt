package com.trainee.project.swiggy.repository.dao.model.repository

import androidx.lifecycle.LiveData
import com.trainee.project.swiggy.repository.dao.model.dao.UserDao
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUserDataRepo(userDeatailsData: UserDeatailsData) {
        userDao.insertUserDao(userDeatailsData)
    }

     fun getUserById(number: String): LiveData<UserDeatailsData> {
        return userDao.getUserById(number)
    }

    // Function to check if phone number exists in the database
    fun isPhoneNumberExists(phoneNumber: String): LiveData<Boolean> {
        return userDao.isPhoneNumberExists(phoneNumber)
    }

}