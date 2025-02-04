package com.trainee.project.swiggy.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.adapter.FoodChildAdapter
import com.trainee.project.swiggy.cart.AddToCart
import com.trainee.project.swiggy.repository.dao.model.model.FoodItemsData
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.viewmodel.FoodViewModel

class FoodItemsTypesActivity : AppCompatActivity(), FoodTypeListener {

    private lateinit var foodName: TextView
    private lateinit var foodDeliveryTime: TextView
    private lateinit var foodDeliveryDistance: TextView
    private lateinit var shopAddress: TextView
    private lateinit var foodRatingText: TextView
    private lateinit var foodTotalRatingGet: TextView
    private lateinit var searchText: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodChildItemsList: MutableList<FoodTypeData>
    private lateinit var foodChildAdapter: FoodChildAdapter
    private lateinit var viewModel: FoodViewModel
    private lateinit var cartLayout: LinearLayout
    private lateinit var itemsNumber: TextView
    private lateinit var cuisine: TextView
    private lateinit var backArrowBtn: ImageView
    private lateinit var fourAboveRating: CardView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_subway_child)

        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        foodName = findViewById(R.id.foodNameHead)
        foodDeliveryTime = findViewById(R.id.foodDeliveryTime)
        foodDeliveryDistance = findViewById(R.id.foodDeliveryDistance)
        shopAddress = findViewById(R.id.shopAddress)
        cuisine = findViewById(R.id.cusine)
        foodRatingText = findViewById(R.id.foodRatingText)
        foodTotalRatingGet = findViewById(R.id.foodTotalRatingGet)
        searchText = findViewById(R.id.searchText)
        backArrowBtn = findViewById(R.id.backArrow_child)

        recyclerView = findViewById(R.id.recylerViewSubwayItems)
        cartLayout = findViewById(R.id.cartLayout)
        itemsNumber = findViewById(R.id.itemsNumber)
        fourAboveRating = findViewById(R.id.fourAboveRating)

        val food: FoodItemsData? = intent.getParcelableExtra("food")
        val childList: List<FoodTypeData>? = food?.childList

        foodChildItemsList = childList?.toMutableList() ?: mutableListOf()

        foodName.text = food?.name
        foodDeliveryTime.text = food?.deliveryTime
        foodDeliveryDistance.text = "•${food?.distance}km"
        shopAddress.text = "•${food?.location}"
        cuisine.text = "${food?.cuisine}"
        foodRatingText.text = "${food?.rating}"
        foodTotalRatingGet.text = "${food?.totalRatings}K+ ratings"

        viewModel.getAllCartItems()
        viewModel.allCartItems.observe(this, Observer { foodList ->

            val currentRestaurantList = foodList.filter { it.restaurantId == food?.id }

            val updatedFoodList = food?.childList?.map { foodChild ->

                val cartItem =
                    currentRestaurantList.find { it.restaurantId == foodChild.restaurantId && it.id == foodChild.id }
                if (cartItem != null) {
                    foodChild.quantity = cartItem.quantity
                } else {
                    foodChild.quantity = 0
                }

                foodChild
            } ?: listOf()

            val quantity = updatedFoodList.sumOf { it.quantity }
            updateCartLayoutVisibility(quantity)

            foodChildAdapter = FoodChildAdapter(updatedFoodList.toMutableList(), this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = foodChildAdapter
        })

        cartLayout.setOnClickListener {
            val intent = Intent(this, AddToCart::class.java)
            startActivity(intent)

        }

        backArrowBtn.setOnClickListener {
            finish()
        }

        fourAboveRating.setOnClickListener {

//            fourAboveRating.cardBackgroundColor.defaultColor
            fourAboveRating.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
            val filteredList = foodChildItemsList.filter {
                it.rating > 4.0
            }

            foodChildAdapter.updateList(filteredList.toMutableList())
            foodChildAdapter.notifyDataSetChanged()
        }

        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                filterFood(p0.toString())
            }
        })
    }

    private fun filterFood(query: String) {
        val filteredList = foodChildItemsList.filter {
            it.name.contains(query, ignoreCase = true)
        }

        foodChildAdapter.updateList(filteredList.toMutableList())
        foodChildAdapter.notifyDataSetChanged()
    }

    private fun updateCartLayoutVisibility(quantity: Int) {
        cartLayout.visibility = if (quantity > 0) View.VISIBLE else View.GONE
        itemsNumber.text = quantity.toString()
    }

    override fun incrementCount(foodTypeData: FoodTypeData) {
        if (foodTypeData.quantity == 0) {
            viewModel.deleteCartItem(foodTypeData.id)
        } else {
            viewModel.updateCartQuantity(foodTypeData.id, foodTypeData.quantity)
        }
        viewModel.getAllCartItems()
    }

    override fun addCardView(item: FoodTypeData) {
        viewModel.insertFoodItemsViewModel(item)
        viewModel.getAllCartItems()
    }

    override fun decrementCount(items: FoodTypeData) {
        if (items.quantity == 0) {
            viewModel.deleteCartItem(items.id)
        } else {
            viewModel.updateCartQuantity(items.id, items.quantity)
        }
        viewModel.getAllCartItems()
    }


    override fun onStart() {
        super.onStart()
        viewModel.getAllCartItems()
    }
}
