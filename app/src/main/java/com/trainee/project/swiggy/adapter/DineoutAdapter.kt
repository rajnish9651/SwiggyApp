package com.trainee.project.swiggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.repository.dao.model.model.DineoutModel

class DineoutAdapter(var dineoutList: MutableList<DineoutModel>) : RecyclerView.Adapter<DineoutAdapter.DineoutViewHolder>() {
    class DineoutViewHolder(items:View) :RecyclerView.ViewHolder(items){

        var name:TextView=items.findViewById(R.id.dineoutNameTop)
        var imgDineout:ImageView=items.findViewById(R.id.imgDineout)
        var imgDown:ImageView=items.findViewById(R.id.imgDown)
        var dineoutNameDown:TextView=items.findViewById(R.id.dineoutNameDown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DineoutViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.dineout_items,parent,false)

        return DineoutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dineoutList.size
    }

    override fun onBindViewHolder(holder: DineoutViewHolder, position: Int) {

        var item=dineoutList[position]
        holder.name.text=item.nameTop
        holder.dineoutNameDown.text=item.nameDown
//        holder.imgDineout.text=item.
//        Glide.with(holder.foodImg).load(food.imgUrl).into(holder.foodImg)
        Glide.with(holder.imgDineout).load(item.imgTop).into(holder.imgDineout)
        Glide.with(holder.imgDown).load(item.imgDown).into(holder.imgDown)

    }
}