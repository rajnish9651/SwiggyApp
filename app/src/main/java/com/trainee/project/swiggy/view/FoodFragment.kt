package com.trainee.project.swiggy.view

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.adapter.FoodItemsAdapter
import com.trainee.project.swiggy.adapter.FoodOfferZoneHorizontalAdapter
import com.trainee.project.swiggy.profile.UserDeatails
import com.trainee.project.swiggy.repository.dao.model.model.FoodItemsData
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.viewmodel.FoodViewModel
import java.util.Locale


class FoodFragment : Fragment() {

    lateinit var foodItemsAdapter: FoodItemsAdapter
    lateinit var foodItemsList: MutableList<FoodItemsData>
    lateinit var recyclerViewFood: RecyclerView
    lateinit var viewModel: FoodViewModel
//    lateinit var profile: ImageView
    lateinit var mic: ImageView
    lateinit var searchView: EditText
    lateinit var horizontalRecyclerView: RecyclerView
    lateinit var horizontalAdapter: FoodOfferZoneHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]


        foodItemsList = mutableListOf()

        val view = inflater.inflate(R.layout.fragment_food, container, false)
        recyclerViewFood = view.findViewById(R.id.recyclerViewFood)
//        profile = view.findViewById(R.id.profile)
        mic = (activity as AppCompatActivity).findViewById(R.id.mic)
        searchView = (activity as AppCompatActivity).findViewById(R.id.searchView)
        horizontalRecyclerView = view.findViewById(R.id.offerRecylerView)


//        profile.setOnClickListener {
//            val intent = Intent(requireActivity(), UserDeatails::class.java)
//            startActivity(intent)
//        }



        foodItemsList.add(
            FoodItemsData(
                id = 0,
                name = "Pizza",
                rating = 4.5,
                totalRatings = 120,
                deliveryTime = "20-25 mins",
                cuisine = "Italian, Fast Food",
                location = "Sector 15",
                discount = "50% OFF",
                distance = 1.2,
                pricePerItem = 299,
                freeDelivery = true,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_660/2f2a8e30d98db46c6507105f68151cf9",
                childList = listOf(
                    FoodTypeData(
                        id = 1,
                        restaurantId = 0,
                        restaurantName = "Pizza",
                        name = "Chicken pizza",
                        price = 199.0,
                        rating = 4.3,
                        totalRatings = 100,
                        size = "Medium",
                        brand = "McDonald's",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/z0c4xpovma1qtmu4tlls",
                        additionalDescription = "Spicy chicken patty with lettuce and mayo.",
                        quantity = 0 // Initialize quantity to 0
                    ),
                    FoodTypeData(
                        id = 2,
                        restaurantId = 0,
                        restaurantName = "Pizza",
                        name = "Veg Pizza",
                        price = 149.0,
                        rating = 4.0,
                        totalRatings = 80,
                        size = "Small",
                        brand = "McDonald's",
                        saveToEatList = false,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/dpventcrozvfni0lqb10",
                        additionalDescription = "Veg patty with cheese and vegetables.",
                        quantity = 0 // Initialize quantity to 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 1,
                name = "Burger",
                rating = 4.2,
                totalRatings = 80,
                deliveryTime = "15-20 mins",
                cuisine = "Fast Food, American",
                location = "Sector 12",
                discount = "₹20 OFF",
                distance = 0.8,
                pricePerItem = 149,
                freeDelivery = false,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_660/RX_THUMBNAIL/IMAGES/VENDOR/2024/6/11/4c140390-e252-47b2-a5aa-6d199918bb10_170196.JPG",
                childList = listOf(
                    FoodTypeData(
                        id = 3,
                        restaurantId = 1,
                        restaurantName = "Burger",
                        name = "Chicken Burger",
                        price = 199.0,
                        rating = 4.3,
                        totalRatings = 100,
                        size = "Medium",
                        brand = "McDonald's",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/67aa20d5bff8c2d59a5ebd3e700daa25",
                        additionalDescription = "Spicy chicken patty with lettuce and mayo.",
                        quantity = 0 // Initialize quantity to 0
                    ),
                    FoodTypeData(
                        id = 4,
                        restaurantId = 1,
                        restaurantName = "Burger",
                        name = "Veg Burger",
                        price = 149.0,
                        rating = 4.0,
                        totalRatings = 80,
                        size = "Small",
                        brand = "McDonald's",
                        saveToEatList = false,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/FOOD_CATALOG/IMAGES/CMS/2024/4/21/0afddda3-b5df-4ab6-a4b8-901bcd37202f_bded6a3a-e583-4c31-87b9-17e2f27f5b4a.png",
                        additionalDescription = "Veg patty with cheese and vegetables.",
                        quantity = 0 // Initialize quantity to 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 2,
                name = "Tea",
                rating = 4.8,
                totalRatings = 50,
                deliveryTime = "10-15 mins",
                cuisine = "Beverages",
                location = "Sector 10",
                discount = "₹90 OFF",
                distance = 0.5,
                pricePerItem = 30,
                freeDelivery = true,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_112,h_112,c_fill/Autosuggest/Top%20200%20queries/Tea.png",
                childList = listOf(
                    FoodTypeData(
                        id = 5,
                        restaurantId = 2,
                        restaurantName = "Tea",
                        name = "Masala Tea",
                        price = 35.0,
                        rating = 4.6,
                        totalRatings = 40,
                        size = "Cup",
                        brand = "Tea Master",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/274733bf12264c7eba4674b68927c346",
                        additionalDescription = "Strong tea with aromatic spices.",
                        quantity = 0 // Initialize quantity to 0
                    ),
                    FoodTypeData(
                        id = 6,
                        restaurantId = 2,
                        restaurantName = "Tea",
                        name = "Lemon Tea",
                        price = 30.0,
                        rating = 4.5,
                        totalRatings = 60,
                        size = "Cup",
                        brand = "Tea Master",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/5ca2285ee03cc661c783fa13a6b087d5",
                        additionalDescription = "Refreshing lemon-flavored tea.",
                        quantity = 0 // Initialize quantity to 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 3,
                name = "Samosa",
                rating = 4.3,
                totalRatings = 150,
                deliveryTime = "15-20 mins",
                cuisine = "Snacks, Indian",
                location = "Sector 14",
                discount = "40% OFF",
                distance = 1.0,
                pricePerItem = 20,
                freeDelivery = true,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_660/RX_THUMBNAIL/IMAGES/VENDOR/2024/10/24/9da9fe9c-29df-4117-b19c-2d09d0663a23_10815.JPG",
                childList = listOf(
                    FoodTypeData(
                        id = 7,
                        restaurantId = 3,
                        restaurantName = "Samosa",
                        name = "Paneer Samosa",
                        price = 25.0,
                        rating = 4.8,
                        totalRatings = 200,
                        size = "Regular",
                        brand = "Bikanervala",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/35b0db3993198b9eb585713b3e3a58e1",
                        additionalDescription = "Crispy samosas with a rich paneer filling.",
                        quantity = 0
                    ),
                    FoodTypeData(
                        id = 8,
                        restaurantId = 3,
                        restaurantName = "Samosa",
                        name = "Aloo Samosa",
                        price = 20.0,
                        rating = 4.5,
                        totalRatings = 150,
                        size = "Regular",
                        brand = "Bikanervala",
                        saveToEatList = false,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/FOOD_CATALOG/IMAGES/CMS/2024/10/1/da0e231f-0195-4470-8f60-90186bfb22b0_47b66dc3-c41f-4a05-832c-683b3c62bbfc.jpg",
                        additionalDescription = "Aloo stuffed samosa with spicy masala.",
                        quantity = 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 4,
                name = "Chicken Delight",
                rating = 4.5,
                totalRatings = 250,
                deliveryTime = "25-30 mins",
                cuisine = "Non-Veg, Indian",
                location = "Sector 22",
                discount = "30% OFF",
                distance = 2.5,
                pricePerItem = 150,
                freeDelivery = false,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/js8ruj7mtsqkb6wxbxew",
                childList = listOf(
                    FoodTypeData(
                        id = 9,
                        restaurantId = 4,
                        restaurantName = "Chicken Gravy",
                        name = "Tandoori Chicken",
                        price = 250.0,
                        rating = 4.7,
                        totalRatings = 180,
                        size = "Half Plate",
                        brand = "Spicy Grill",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,h_600/ab482d4bd0e8968726da6f8ad4029f82",
                        additionalDescription = "Juicy and smoky tandoori chicken with authentic spices.",
                        quantity = 0
                    ),
                    FoodTypeData(
                        id = 10,
                        restaurantId = 4,
                        restaurantName = "Butter Chicken",
                        name = "Butter Chicken",
                        price = 300.0,
                        rating = 4.8,
                        totalRatings = 220,
                        size = "Full Plate",
                        brand = "North Indian Delights",
                        saveToEatList = false,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/81e6d6875066ba4dc01fc9f9c911e58d",
                        additionalDescription = "Creamy butter chicken cooked with rich tomato gravy.",
                        quantity = 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 5,
                name = "Kulfi Corner",
                rating = 4.6,
                totalRatings = 300,
                deliveryTime = "10-15 mins",
                cuisine = "Desserts, Indian",
                location = "Sector 10",
                discount = "20% OFF",
                distance = 1.2,
                pricePerItem = 50,
                freeDelivery = true,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/06e52b865215c853ce4f301130e7f522",
                childList = listOf(
                    FoodTypeData(
                        id = 11,
                        restaurantId = 5,
                        restaurantName = "Kulfi Corner",
                        name = "Kesar Pista Kulfi",
                        price = 60.0,
                        rating = 4.8,
                        totalRatings = 150,
                        size = "Regular",
                        brand = "Chill Delights",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/60d3ae1670579b3b224c7f1433739d01",
                        additionalDescription = "Creamy mango kulfi made with fresh mangoes and milk.",
                        quantity = 0
                    ),
                    FoodTypeData(
                        id = 12,
                        restaurantId = 5,
                        restaurantName = "Kulfi Corner",
                        name = "Matka Kulfi",
                        price = 70.0,
                        rating = 4.7,
                        totalRatings = 180,
                        size = "Large",
                        brand = "Chill Delights",
                        saveToEatList = false,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/f14db2ef2a43b61245d3ff6f6df7c108",
                        additionalDescription = "Rich pista kulfi topped with chopped nuts.",
                        quantity = 0
                    )
                )
            )
        )

        foodItemsList.add(
            FoodItemsData(
                id = 6,
                name = "Cake Delight",
                rating = 4.7,
                totalRatings = 500,
                deliveryTime = "20-25 mins",
                cuisine = "Desserts, Bakery",
                location = "Sector 5",
                discount = "25% OFF",
                distance = 3.0,
                pricePerItem = 300,
                freeDelivery = false,
                imgUrl = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/FOOD_CATALOG/IMAGES/CMS/2024/9/15/2c4d22bb-70d3-4927-9c44-d96fb5581c64_77029d61-176a-45e4-8194-6c8ea674ab35.png",
                childList = listOf(
                    FoodTypeData(
                        id = 13,
                        restaurantId = 6,
                        restaurantName = "Cake Delight",
                        name = "Chocolate Truffle Cake",
                        price = 400.0,
                        rating = 4.9,
                        totalRatings = 300,
                        size = "1 Pound",
                        brand = "Sweet Bakes",
                        saveToEatList = true,
                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/FOOD_CATALOG/IMAGES/CMS/2024/9/15/1fd5fb22-aaa3-47e8-9ad4-87d9cb8b9ddf_7ab8f7bc-ae9b-482f-8089-a48ca72ad597.png",
                        additionalDescription = "Rich and moist chocolate truffle cake with layers of ganache.",
                        quantity = 0
                    ),
                    FoodTypeData(
                        id = 14,
                        restaurantId = 6,
                        restaurantName = "Cake Delight",
                        name = "Butterscotch Cake",
                        price = 450.0,
                        rating = 4.8,
                        totalRatings = 200,
                        size = "1 Pound",
                        brand = "Sweet Bakes",
                        saveToEatList = false,

                        imageUrlChild = "https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300,h_300,c_fit/FOOD_CATALOG/IMAGES/CMS/2024/9/15/ebd0a95d-0efa-4e10-b8af-c4efd536d29d_12eafb72-bb6b-489d-ade1-9fb5c2bc4a3a.png",
                        additionalDescription = "Classic red velvet cake topped with cream cheese frosting.",
                        quantity = 0
                    )
                )
            )
        )




        horizontalAdapter = FoodOfferZoneHorizontalAdapter(foodItemsList)
        horizontalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = horizontalAdapter


        foodItemsAdapter = FoodItemsAdapter(foodItemsList)

        recyclerViewFood.layoutManager = LinearLayoutManager(requireActivity())
        recyclerViewFood.adapter = foodItemsAdapter


        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
//
                filterFood(p0.toString())
            }

        })

        //speech
        mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            }

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
//                Toast.makeText(requireActivity(), " ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    //filtering the food or search
    private fun filterFood(query: String) {

        var filterList = foodItemsList.filter {
            it.name.contains(
                query,
                ignoreCase = true
            ) || it.cuisine.contains(query, ignoreCase = true)
        }
        foodItemsAdapter.updateList(filterList)
        foodItemsAdapter.notifyDataSetChanged()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                searchView.setText(result?.get(0))
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_SPEECH_INPUT = 1
    }


}