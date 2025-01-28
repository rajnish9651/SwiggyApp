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

class FoodItemsAdapter(private var arrayList: MutableList<FoodItemsData>) :
    RecyclerView.Adapter<FoodItemsAdapter.FoodItemsViewHolder>() {

    class FoodItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.itemsName)
        var itemsRating: TextView = itemView.findViewById(R.id.itemsRating)
        var itemsTotalRating: TextView = itemView.findViewById(R.id.itemsTotalRating)
        var itemsDeliveredTime: TextView = itemView.findViewById(R.id.itemsDeliveredTime)
        var cuisineName: TextView = itemView.findViewById(R.id.cuisineName)
        var location: TextView = itemView.findViewById(R.id.location)
        var distance: TextView = itemView.findViewById(R.id.distance)
        var itemsdiscount: TextView = itemView.findViewById(R.id.itemsdiscount)
        var foodImg: ImageView = itemView.findViewById(R.id.foodImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item_recyclerview, parent, false);
        return FoodItemsViewHolder(view);
    }

    override fun getItemCount(): Int {
        return arrayList.size;
    }

    override fun onBindViewHolder(holder: FoodItemsViewHolder, position: Int) {

        var food = arrayList[position]
        holder.name.text = food.name

        holder.name.text = food.name
        holder.itemsRating.text = " ${food.rating}"
        holder.itemsTotalRating.text = "(${food.totalRatings})"
        holder.itemsDeliveredTime.text = "${food.deliveryTime}"
        holder.cuisineName.text = "${food.cuisine}"
        holder.location.text = "${food.location}"
        holder.distance.text = " ${food.distance} km"
        holder.itemsdiscount.text = " ${food.discount}"

        Glide.with(holder.foodImg).load(food.imgUrl).into(holder.foodImg)

        holder.itemView.setOnClickListener {

            // Start FoodItemsTypesActivity with the food data
            val context = holder.itemView.context
            val intent = Intent(context, FoodItemsTypesActivity::class.java)
            intent.putExtra("food", food)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }
    }

    fun updateList(newitemsList: List<FoodItemsData>) {
        arrayList = newitemsList.toMutableList()
        notifyDataSetChanged()
    }
}