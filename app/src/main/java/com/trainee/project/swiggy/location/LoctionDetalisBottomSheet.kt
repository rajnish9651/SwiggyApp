package com.trainee.project.swiggy.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trainee.project.swiggy.R
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData
import com.trainee.project.swiggy.viewmodel.UserSavedLocationViewModel


class LoctionDetalisBottomSheet : BottomSheetDialogFragment() {

    lateinit var address: String
    lateinit var phoneNumber: String
    lateinit var addressViewModel: UserSavedLocationViewModel
    lateinit var HouseNo: EditText
    lateinit var road: EditText
    lateinit var home: CardView
    lateinit var work: CardView
    lateinit var family: CardView
    lateinit var other: CardView
    lateinit var confirmLocationBtn: CardView
    lateinit var savedAddressClickListner: SavedAddressClickListner

    var locationType: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure that the activity implements the SavedAddressClickListener interface
        if (context is SavedAddressClickListner) {
            savedAddressClickListner = context
        } else {
            throw RuntimeException("$context must implement SavedAddressClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_loction_detalis_bottom_sheet, container, false)

        HouseNo = view.findViewById(R.id.HouseNo)
        road = view.findViewById(R.id.road)
        home = view.findViewById(R.id.home)
        work = view.findViewById(R.id.work)
        family = view.findViewById(R.id.family)
        other = view.findViewById(R.id.other)
        confirmLocationBtn = view.findViewById(R.id.confirmLocationBtn)

        address = arguments?.getString("address").toString()
        phoneNumber = arguments?.getString("phoneNumber").toString()

        addressViewModel = ViewModelProvider(this)[UserSavedLocationViewModel::class.java]

        home.setOnClickListener {
            locationType = "home"
            highlightSelectedCard(home)
        }

        work.setOnClickListener {
            locationType = "work"
            highlightSelectedCard(work)
        }

        other.setOnClickListener {
            locationType = "other"
            highlightSelectedCard(other)
        }

        confirmLocationBtn.setOnClickListener {

            val houseNoValue: String = if (HouseNo.text.toString().isEmpty()) "" else HouseNo.text.toString()
            val roadValue: String = if (road.text.toString().isEmpty()) "" else road.text.toString()
            val locationTypeToSave = locationType ?: "home"

            addressViewModel.saveLocationToDb(
                UserSavedLocationData(
                    0,
                    phoneNumber,
                    address,
                    houseNoValue,
                    roadValue,
                    locationTypeToSave
                )
            )

            dismiss()

            // Notify the activity that the address is saved
            savedAddressClickListner.onSavedAddressConfirmed()
        }

        return view
    }

    private fun highlightSelectedCard(selectedCard: CardView) {
        home.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.default_card_background))
        work.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.default_card_background))
        other.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.default_card_background))

        when (selectedCard) {
            home -> selectedCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.home_color))
            work -> selectedCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.work_color))
            other -> selectedCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.other_color))
        }
    }
}
