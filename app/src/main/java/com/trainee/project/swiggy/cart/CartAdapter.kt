package com.trainee.project.swiggy.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.databinding.CartItemsRecylerViewBinding
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.view.CartItemListener

class CartAdapter(
    private val arrayList: MutableList<FoodTypeData>,
    private val cartItemListener: CartItemListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemsRecylerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //        // Helper function to update item view visibility
        fun updateItemView(cartItem: FoodTypeData) {
            with(binding) {
                if (cartItem.quantity > 0) {
//
                    itemCountText.text = "${cartItem.quantity}"
                } else {
//
                    itemCountText.text = "${cartItem.quantity}"
                }
            }
        }

        fun bind(cartItem: FoodTypeData, cartItemListener: CartItemListener) {
            with(binding) {
                // Bind data to the views
                itemName.text = cartItem.name
                price.text = "₹${cartItem.price * cartItem.quantity}"
                itemCountText.text = "${cartItem.quantity}"

                // Initial visibility setup based on quantity
                updateItemView(cartItem)

                // Handle add item to cart
                addItem.setOnClickListener {
                    cartItem.quantity++
                    cartItemListener.addItemClick(cartItem)
//                    notifyItemChanged(adapterPosition)
                    updateItemView(cartItem)
                }


                // Handle remove item from cart
                removeItem.setOnClickListener {
                    if (cartItem.quantity > 0) {
                        cartItem.quantity--
                        cartItemListener.removeItemClick(cartItem)
//                        notifyItemChanged(adapterPosition)
                    }
                    updateItemView(cartItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemsRecylerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = arrayList[position]
        holder.bind(cartItem, cartItemListener)


    }
}
