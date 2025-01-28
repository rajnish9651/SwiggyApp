package com.trainee.project.swiggy.repository.dao.model.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.trainee.project.swiggy.convertor.Converters
import kotlinx.parcelize.Parcelize


@Entity(tableName = "FoodItems")
@Parcelize
data class FoodItemsData(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val rating: Double,
    val totalRatings: Int,
    val deliveryTime: String,
    val cuisine: String,
    val location: String,
    val distance: Double,
    val pricePerItem: Int,
    val discount:String,
    val freeDelivery: Boolean,
    val imgUrl:String,
    @TypeConverters(Converters::class)
    val childList: List<FoodTypeData>
) : Parcelable
