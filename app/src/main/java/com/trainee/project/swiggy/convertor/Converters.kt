package com.trainee.project.swiggy.convertor


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData

class Converters {

    // Convert List<FoodChildItemsData> to JSON string
    @TypeConverter
    fun fromFoodChildItemsList(value: List<FoodTypeData>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<FoodTypeData>>() {}.type
        return gson.toJson(value, type)
    }

    // Convert JSON string back to List<FoodChildItemsData>
    @TypeConverter
    fun toFoodChildItemsList(value: String): List<FoodTypeData> {
        val gson = Gson()
        val type = object : TypeToken<List<FoodTypeData>>() {}.type
        return gson.fromJson(value, type)
    }
}
