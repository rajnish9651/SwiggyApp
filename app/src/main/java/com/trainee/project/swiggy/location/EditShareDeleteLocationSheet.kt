package com.trainee.project.swiggy.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trainee.project.swiggy.R

class EditShareDeleteLocationSheet : BottomSheetDialogFragment() {

   var address:String? = null
   var id:Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view= inflater.inflate(R.layout.fragment_edit_share_delete_location_sheet, container, false)



        address = arguments?.getString("address").toString()
        id = arguments?.getInt("id")







        return view
    }


}