package com.trainee.project.swiggy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trainee.project.swiggy.repository.dao.model.DBHelper
import com.trainee.project.swiggy.repository.dao.model.repository.Repository
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    val dbHelperInstance = DBHelper.getDatabase(application)
    val repository = Repository(dbHelperInstance.foodDao())

    private val _allCartItemsMutable = MutableLiveData<List<FoodTypeData>>()
    val allCartItems: LiveData<List<FoodTypeData>> = _allCartItemsMutable

    // Retrieve all cart items
    fun getAllCartItems() {
        viewModelScope.launch {
            val items = async {
                repository.getAllCartItems()
            }.await()
            _allCartItemsMutable.postValue(items)
        }
    }


    // Update cart item quantity
    fun updateCartQuantity(id: Int, quantity: Int) = viewModelScope.launch(Dispatchers.IO) {
        // Update the quantity of the item in the database
        repository.updateCartQuantity(id, quantity)

        // Refresh the cart items
        getAllCartItems()
    }

    // Delete a cart item by its ID
    fun deleteCartItem(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCartItem(id)

        // Refresh the cart items
        getAllCartItems()
    }

    fun insertFoodItemsViewModel(item: FoodTypeData) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFoodItemsRepo(item)
            getAllCartItems()
        }
    }


    fun getCartItemById(itemId: Int): FoodTypeData {
         return repository.getCartItemById(itemId)
    }

    fun deleteAllCartItemViewModel(){
        viewModelScope.launch {
            repository.deleteAllCartItemRepo()
        }
    }

}
