package com.trainee.project.swiggy.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.login.LoginLaunchActivity
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.view.CartItemListener
import com.trainee.project.swiggy.viewmodel.FoodViewModel

class AddToCart : AppCompatActivity(), CartItemListener {

    lateinit var cartRecylerView: RecyclerView
    lateinit var viewModel: FoodViewModel
    lateinit var cartItemList: MutableList<FoodTypeData>
    lateinit var cartAdapter: CartAdapter

    lateinit var expandableLayout: LinearLayout
    lateinit var expandIcon: ImageView
    lateinit var totalPayTop: TextView
    lateinit var totalPayAmmount: TextView
    lateinit var priceToProceed: TextView
    lateinit var locationBox: LinearLayout
    lateinit var loginCart: LinearLayout
    lateinit var locationBoxBtn: CardView
    lateinit var proceedOrder: RelativeLayout
    lateinit var placeOrderBtn: CardView
    lateinit var orderGif: ImageView
    var totalPrice: Int = 0

    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        cartRecylerView = findViewById(R.id.cartRecylerView)
        expandableLayout = findViewById(R.id.itemsList)
        expandIcon = findViewById(R.id.expand_icon)
        totalPayTop = findViewById(R.id.totalPayTop)
        totalPayAmmount = findViewById(R.id.totalPayAmmount)
        priceToProceed = findViewById(R.id.priceToProceed)
        locationBox = findViewById(R.id.locationBox)
        proceedOrder = findViewById(R.id.proceedOrder)
        locationBoxBtn = findViewById(R.id.locationBoxBtn)
        placeOrderBtn = findViewById(R.id.placeOrderBtn)
        loginCart = findViewById(R.id.loginCart)
        orderGif = findViewById(R.id.orderGif)

        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("user_phone", null)

        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        cartItemList = mutableListOf()

        viewModel.getAllCartItems()
        viewModel.allCartItems.observe(this, Observer { list ->

            if (list != null) {
                cartItemList.clear()
                cartItemList.addAll(list)

            }

            // total price every time the cart items change
            totalPrice = cartItemList.sumOf { it.price.toInt() * it.quantity }
            totalPayTop.text = "To Pay ${totalPrice}"
            totalPayAmmount.text = "₹$totalPrice"
            priceToProceed.text = "₹$totalPrice"
            Log.d("test", cartItemList.toString())
        })

        cartAdapter = CartAdapter(cartItemList, this)
        cartRecylerView.layoutManager = LinearLayoutManager(this)
        cartRecylerView.adapter = cartAdapter


        // Set click listener for header
        expandIcon.setOnClickListener {
            if (isExpanded) {
                // Collapse the layout
                expandableLayout.visibility = View.GONE
                expandIcon.rotation = 0f // Reset arrow rotation
            } else {
                // Expand the layout
                expandableLayout.visibility = View.VISIBLE
                expandIcon.rotation = 180f // Rotate arrow
            }
            isExpanded = !isExpanded // Toggle state
        }


        if (phoneNumber==null) {
            loginCart.setOnClickListener {
                var intent =Intent(this@AddToCart,LoginLaunchActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            loginCart.visibility=View.GONE
            locationBox.visibility = View.VISIBLE

            locationBoxBtn.setOnClickListener {
            locationBox.visibility = View.GONE
//            loginCart.visibility = View.GONE
            proceedOrder.visibility = View.VISIBLE
        }
        }



        placeOrderBtn.setOnClickListener {
            Glide.with(orderGif).load(R.drawable.success).into(orderGif)
            orderGif.visibility = View.VISIBLE
            orderGif.postDelayed({
                orderGif.visibility = View.GONE

                viewModel.deleteAllCartItemViewModel()
//                var intent = Intent(this, FoodFragment::class.java)
//                startActivity(intent)

                finish()
            }, 3000)
        }

    }

    override fun addItemClick(foodTypeData: FoodTypeData) {
        if (foodTypeData.quantity == 0) {
            viewModel.deleteCartItem(foodTypeData.id)
        } else {
            viewModel.updateCartQuantity(foodTypeData.id, foodTypeData.quantity)
        }
        // Refresh the cart items
        viewModel.getAllCartItems()
    }

    override fun removeItemClick(foodTypeData: FoodTypeData) {
        if (foodTypeData.quantity == 0) {
            viewModel.deleteCartItem(foodTypeData.id)
        } else {
            viewModel.updateCartQuantity(foodTypeData.id, foodTypeData.quantity)
        }
        // Refresh the cart items
        viewModel.getAllCartItems()
    }
}