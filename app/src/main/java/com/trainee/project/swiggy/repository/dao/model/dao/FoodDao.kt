package com.trainee.project.swiggy.repository.dao.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData

@Dao
interface FoodDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItemsDao(item: FoodTypeData)

    @Update
    suspend fun updateCartItem(foodTypeData: FoodTypeData)

    @Query("UPDATE FoodList SET quantity = :quantity WHERE id = :id")
    suspend fun updateCartQuantity(id: Int, quantity: Int)

    @Query("delete from FoodList where id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("SELECT * FROM FoodList")
    suspend fun getAllCartItems(): List<FoodTypeData>

    @Query("SELECT * FROM FoodList WHERE id = :itemId LIMIT 1")
    fun getItemById(itemId: Int): FoodTypeData

    @Query("delete from FoodList")
    suspend fun deleteAllCartItem()

}
