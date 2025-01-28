package com.trainee.project.swiggy.view

import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData

interface CartItemListener {


    fun addItemClick(foodTypeData: FoodTypeData)
    fun removeItemClick(foodTypeData: FoodTypeData)

}