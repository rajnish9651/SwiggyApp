package com.trainee.project.swiggy.repository.dao.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users_Location")
data class UserSavedLocationData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val moblineNo: String,
    val address: String,
    val HouseNo: String,
    val road: String,
    val work: String
)