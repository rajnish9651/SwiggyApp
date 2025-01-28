package com.trainee.project.swiggy.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trainee.project.swiggy.R

class BottomSheet: BottomSheetDialogFragment() {

    private var currentCount: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       var view=inflater.inflate(R.layout.bottom_cart_items,container,false)
        val itemsNumberTextView:TextView = view.findViewById(R.id.itemsNumber)


        currentCount = arguments?.getInt("currentCount") ?: 0


        return view




    }



}