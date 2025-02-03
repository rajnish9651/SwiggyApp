package com.trainee.project.swiggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.location.SavedAdresssActivity
import com.trainee.project.swiggy.location.SavedProfileAddressListener
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData

class SavedAddressProfileAdapter(
    var arraylist: MutableList<UserSavedLocationData>,
    var savedProfileAddressListener: SavedProfileAddressListener
) :
    RecyclerView.Adapter<SavedAddressProfileAdapter.SavedAdressViewHolder>() {


    class SavedAdressViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        var addressProfile: TextView = items.findViewById(R.id.addressProfile)
        var editAddress: TextView = items.findViewById(R.id.editAddress)
        var deleteAddress: TextView = items.findViewById(R.id.deleteAddress)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAdressViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_saved_address_recylerview_item, parent, false)
        return SavedAdressViewHolder(view)
    }



    override fun onBindViewHolder(holder: SavedAdressViewHolder, position: Int) {
        var address=arraylist[position]
        holder.addressProfile.text=address.address

//        holder.deleteAddress.setOnClickListener {
//            savedProfileAddressListener.deleteSavedAddrress(address.id,address.moblineNo)
//
//            arraylist.removeAt(position)
//            notifyItemRemoved(position)
//
//        }


    }


    override fun getItemCount(): Int {
        return arraylist.size
    }


}