package com.trainee.project.swiggy.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foodId: Int,
    val foodName: String,
    val foodPrice: Double,
    val quantity: Int,
    val imageUrl: String,
)
