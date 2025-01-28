package com.trainee.project.swiggy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.trainee.project.swiggy.R


class GenieFragment : Fragment() {

    lateinit var imageSlider: ImageSlider
    lateinit var searchCardView: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.genie_layout, container, false)

        imageSlider = view.findViewById(R.id.slider_bottom)
//        searchCardView = (activity as AppCompatActivity).findViewById(R.id.searchCardView)
//        val cardView: CardView = view.findViewById(R.id.genie_imgSliderCard_bottom)


        val sliderImages = mutableListOf(
            SlideModel("https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_660/RX_THUMBNAIL/IMAGES/VENDOR/2024/10/24/9da9fe9c-29df-4117-b19c-2d09d0663a23_10815.JPG","1/3"),
            SlideModel("https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_112,h_112,c_fill/Autosuggest/Top%20200%20queries/Tea.png","2/3"),
            SlideModel("https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_660/RX_THUMBNAIL/IMAGES/VENDOR/2024/6/11/4c140390-e252-47b2-a5aa-6d199918bb10_170196.JPG","3/3")
        )



        imageSlider.setImageList(sliderImages,ScaleTypes.FIT)


//        searchCardView.visibility=View.GONE
        return view
    }


}