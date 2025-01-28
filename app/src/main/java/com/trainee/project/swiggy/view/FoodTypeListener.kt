package com.trainee.project.swiggy.view

import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData

interface FoodTypeListener {

    fun incrementCount(items: FoodTypeData)
    fun addCardView(item: FoodTypeData)
    fun decrementCount(items: FoodTypeData)


}