package com.example.hw4_favoriteanimal

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingFragment() : Fragment() {

    private lateinit var buttonLister : SaveListener
    private lateinit var uiModel : UIModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        uiModel = ViewModelProvider(requireActivity()).get(UIModel::class.java)
        buttonLister = SaveListener()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view!!.findViewById<Button>(R.id.saveButton).setOnClickListener(buttonLister)
    }

    override fun onResume() {
        super.onResume()
        setDisplay()
    }

    fun cancel(){uiModel.currentAnimalID = 0}

    fun setDisplay(){
        var imageID = when(uiModel.currentAnimalID){
            R.id.imgBtnBear -> R.drawable.bear
            R.id.imgBtnCat -> R.drawable.cat
            R.id.imgBtnDog -> R.drawable.dog
            R.id.imgBtnRabbit -> R.drawable.rabbit
            else -> 0
        }
        if(imageID == 0){
            view!!.findViewById<TextView>(R.id.txtNoAnimalSelected).visibility = View.VISIBLE
            view!!.findViewById<ImageView>(R.id.imgExpandedAnimal).visibility = View.INVISIBLE
            view!!.findViewById<Button>(R.id.saveButton).visibility = View.INVISIBLE
            view!!.findViewById<RatingBar>(R.id.ratingBar).visibility = View.INVISIBLE
        }
        else{
            var rating : Float = activity!!.getPreferences(Activity.MODE_PRIVATE).getFloat(
                    uiModel.currentAnimalID.toString(), 0F)
            view!!.findViewById<RatingBar>(R.id.ratingBar).rating = rating
            view!!.findViewById<ImageView>(R.id.imgExpandedAnimal).setImageResource(imageID)
            view!!.findViewById<TextView>(R.id.txtNoAnimalSelected).visibility = View.INVISIBLE
            view!!.findViewById<ImageView>(R.id.imgExpandedAnimal).visibility = View.VISIBLE
            view!!.findViewById<Button>(R.id.saveButton).visibility = View.VISIBLE
            view!!.findViewById<RatingBar>(R.id.ratingBar).visibility = View.VISIBLE
        }
    }

    private inner class SaveListener():View.OnClickListener{
        override fun onClick(v: View?) {
            (activity as MainActivity).ratingSubmitted(
                    uiModel.currentAnimalID,view!!.
                    findViewById<RatingBar>(R.id.ratingBar).rating)
        }
    }
}