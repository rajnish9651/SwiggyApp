package com.trainee.project.swiggy.repository.dao.model.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "FoodList")
data class FoodTypeData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val restaurantId:Int = 0,
    val restaurantName:String,
    var name: String,
    var price: Double,
    var rating: Double,
    var totalRatings: Int,
    val size: String,
    val brand: String,
    val imageUrlChild: String,
    val saveToEatList: Boolean,
    val additionalDescription: String = "",
    var quantity: Int = 0
) : Parcelable
