package com.trainee.project.swiggy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.repository.dao.model.model.FoodItemsData
import com.trainee.project.swiggy.view.FoodItemsTypesActivity

class FoodOfferZoneHorizontalAdapter(var foodItemsList: MutableList<FoodItemsData>) :
    RecyclerView.Adapter<FoodOfferZoneHorizontalAdapter.OfferZoneViewHolder>() {
    class OfferZoneViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        var name: TextView = items.findViewById(R.id.name)
        var rating: TextView = items.findViewById(R.id.rating)
        var time: TextView = items.findViewById(R.id.times)
        var imgChild: ImageView = items.findViewById(R.id.foodImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferZoneViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_items_horizontal_recycleview, parent, false)
        return OfferZoneViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodItemsList.size
    }

    override fun onBindViewHolder(holder: OfferZoneViewHolder, position: Int) {
        var food = foodItemsList[position]
        holder.name.text = food.name
        holder.rating.text = " ${food.rating}"
        holder.time.text = food.deliveryTime

        Glide.with(holder.imgChild).load(food.imgUrl).into(holder.imgChild)

        holder.itemView.setOnClickListener {
            // Start FoodItemsTypesActivity with the food data
            val context = holder.itemView.context
            val intent = Intent(context, FoodItemsTypesActivity::class.java)
            intent.putExtra("food", food)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }

    }
}