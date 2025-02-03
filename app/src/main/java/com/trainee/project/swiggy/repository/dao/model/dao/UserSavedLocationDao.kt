package com.trainee.project.swiggy.repository.dao.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData


@Dao
interface UserSavedLocationDao {

    @Insert
    suspend fun insert(userLocation: UserSavedLocationData)

    @Query("SELECT * FROM Users_Location WHERE moblineNo = :userId")
    suspend fun getLocationsByUser(userId: String): List<UserSavedLocationData>


    // Function to get user locations by phone number
    @Query("SELECT * FROM Users_Location WHERE moblineNo = :phoneNumber")
    fun getUserLocationByPhoneNumber(phoneNumber: String): LiveData<List<UserSavedLocationData>>

    @Query("Delete FROM Users_Location WHERE id=:id")
    suspend fun deleteAddressByIdDao(id: Int)


    @Query("SELECT * FROM Users_Location WHERE moblineNo =:phoneNumber")
   suspend fun getUserAddressDao(phoneNumber:String): List<UserSavedLocationData>


}