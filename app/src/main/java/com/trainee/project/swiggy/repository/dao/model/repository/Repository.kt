package com.trainee.project.swiggy.repository.dao.model.repository

import com.trainee.project.swiggy.repository.dao.model.FoodDao
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData

class Repository(private val foodDao: FoodDao) {


    suspend fun getAllCartItems(): List<FoodTypeData> {
        return foodDao.getAllCartItems()
    }

    suspend fun insertFoodItemsRepo(foodTypeData: FoodTypeData) {
        foodDao.insertFoodItemsDao(foodTypeData)
    }

    suspend fun deleteCartItem(id: Int) {
        foodDao.deleteCartItem(id)
    }

    suspend fun updateCartQuantity(id: Int, quantity: Int) {
        foodDao.updateCartQuantity(id, quantity)
    }



    suspend fun updateCartItem(foodTypeData: FoodTypeData) {
        foodDao.updateCartItem(foodTypeData)
    }

    fun getCartItemById(itemId: Int): FoodTypeData {
        return foodDao.getItemById(itemId)
    }

    suspend fun deleteAllCartItemRepo(){
        foodDao.deleteAllCartItem()
    }



}