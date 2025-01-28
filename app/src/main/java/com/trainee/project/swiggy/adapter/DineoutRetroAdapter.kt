package com.trainee.project.swiggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.repository.dao.model.model.RestaurantsModel

class DineoutRetroAdapter(var restroList: MutableList<RestaurantsModel>) : RecyclerView.Adapter<DineoutRetroAdapter.RetroViewHolder>() {
    class RetroViewHolder(item:View):RecyclerView.ViewHolder(item) {

        var retroType:TextView=item.findViewById(R.id.retroType)
        var addressRestro:TextView=item.findViewById(R.id.addressRestro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetroViewHolder {
        var view =LayoutInflater.from(parent.context).inflate(R.layout.dineout_restaurants_items,parent,false)
        return RetroViewHolder(view)
    }

    override fun getItemCount(): Int {
       return restroList.size
    }

    override fun onBindViewHolder(holder: RetroViewHolder, position: Int) {
      var retro=restroList[position]
        holder.retroType.text=retro.retroType
        holder.addressRestro.text=retro.address
    }
}