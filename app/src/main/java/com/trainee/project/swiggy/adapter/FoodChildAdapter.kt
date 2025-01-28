package com.trainee.project.swiggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trainee.project.swiggy.databinding.ItemsFoodChildSubwaysBinding
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.view.FoodTypeListener

class FoodChildAdapter(
    private var arrayList: List<FoodTypeData>,
    private val foodCart: FoodTypeListener
) : RecyclerView.Adapter<FoodChildAdapter.FoodChildViewHolder>() {

    class FoodChildViewHolder(val binding: ItemsFoodChildSubwaysBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodChildViewHolder {
        val binding =
            ItemsFoodChildSubwaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodChildViewHolder, position: Int) {
        val item = arrayList[position]

        // Binding data to views
        with(holder.binding) {
            foodName.text = item.name
            foodPrice.text = "₹ ${item.price}"
            foodRating.text = "${item.rating}"
            foodTotalRating.text = "(${item.totalRatings})"
            Glide.with(foodImgChild).load(item.imageUrlChild).into(foodImgChild)
            foodDesc.text = item.additionalDescription

            if (item.quantity > 0) {
                removeCardView.visibility = View.VISIBLE
                addCardView.visibility = View.GONE
                itemCountText.text = "${item.quantity}"
            } else {
                removeCardView.visibility = View.GONE
                addCardView.visibility = View.VISIBLE
                itemCountText.text = "${item.quantity}"
            }

            // Handle item add to cart
            addCardView.setOnClickListener {
                item.quantity++
                foodCart.addCardView(item)
                notifyItemChanged(position)

                updateItemView(item, holder)
            }

            //  add item to cart
            addItemtoCart.setOnClickListener {
                item.quantity++
                foodCart.incrementCount(item)
                notifyItemChanged(position)
            }

            //  remove item from cart
            removeItemtoCart.setOnClickListener {
                if (item.quantity > 0) {
                    item.quantity--
                    foodCart.decrementCount(item)
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    // function to update item view
    private fun updateItemView(item: FoodTypeData, holder: FoodChildViewHolder) {
        with(holder.binding) {
            if (item.quantity > 0) {
                removeCardView.visibility = View.VISIBLE
                addCardView.visibility = View.GONE
                itemCountText.text = "${item.quantity}"
            } else {
                removeCardView.visibility = View.GONE
                addCardView.visibility = View.VISIBLE
                itemCountText.text = "${item.quantity}"
            }
        }
    }

    fun updateList(toMutableList: MutableList<FoodTypeData>) {

        arrayList = toMutableList
        notifyDataSetChanged()
    }


}
