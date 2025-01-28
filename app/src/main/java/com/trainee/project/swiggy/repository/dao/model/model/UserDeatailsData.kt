package com.trainee.project.swiggy.repository.dao.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("USER_Details")
data class UserDeatailsData (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var number: String,
    var name:String,
    var email:String

)