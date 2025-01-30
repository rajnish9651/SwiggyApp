package com.trainee.project.swiggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData

class SavedLoactionAdapter(var arraylist:MutableList<UserSavedLocationData>):RecyclerView.Adapter<SavedLoactionAdapter.SavedAdressViewHolder>() {


    class SavedAdressViewHolder(items: View):RecyclerView.ViewHolder(items) {
        var addressText:TextView=items.findViewById(R.id.addresstextview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAdressViewHolder {
       var view =LayoutInflater.from(parent.context).inflate(R.layout.save_address_recylerview,parent,false)
        return SavedAdressViewHolder(view)
    }

    override fun getItemCount(): Int {
       return arraylist.size
    }

    override fun onBindViewHolder(holder: SavedAdressViewHolder, position: Int) {
         var location=arraylist[position]
        holder.addressText.text=location.address
    }
}