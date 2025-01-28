package com.trainee.project.swiggy.repository.dao.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserDao(user: UserDeatailsData)


    @Query("SELECT * FROM USER_Details WHERE number = :number LIMIT 1")
     fun getUserById(number: String):LiveData<UserDeatailsData>


    // Function to check if phone number exists in the database
    @Query("SELECT EXISTS(SELECT 1 FROM user_details WHERE number = :phoneNumber LIMIT 1)")
    fun isPhoneNumberExists(phoneNumber: String): LiveData<Boolean>


}