package com.example.hw4_favoriteanimal

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [AnimalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimalsFragment() : Fragment() {

    private lateinit var animalsClickListener: ImageClickListener
    private lateinit var clearButtonListener: ButtonListener
    private lateinit var uiModel: UIModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_animals, container, false)
        uiModel = ViewModelProvider(requireActivity()).get(UIModel::class.java)
        animalsClickListener = ImageClickListener()
        clearButtonListener = ButtonListener()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateRatings()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        view!!.findViewById<ImageButton>(R.id.imgBtnBear).setOnClickListener(animalsClickListener)
        view!!.findViewById<ImageButton>(R.id.imgBtnCat).setOnClickListener(animalsClickListener)
        view!!.findViewById<ImageButton>(R.id.imgBtnDog).setOnClickListener(animalsClickListener)
        view!!.findViewById<ImageButton>(R.id.imgBtnRabbit).setOnClickListener(animalsClickListener)
        view!!.findViewById<Button>(R.id.clearRatingsButton).setOnClickListener(clearButtonListener)
    }

    fun updateRatings(){
        val prefs : SharedPreferences? = activity!!.getPreferences(Activity.MODE_PRIVATE)
        if(prefs != null) {
            activity!!.findViewById<TextView>(R.id.txtBearRating).text = getString(R.string.yourRating)
            activity!!.findViewById<TextView>(R.id.txtCatRating).text = getString(R.string.yourRating)
            activity!!.findViewById<TextView>(R.id.txtDogRating).text = getString(R.string.yourRating)
            activity!!.findViewById<TextView>(R.id.txtRabbitRating).text = getString(R.string.yourRating)

            if(prefs.contains(R.id.imgBtnBear.toString()))
                activity!!.findViewById<TextView>(R.id.txtBearRating).text =
                    """${getString(R.string.yourRating)} ${prefs.getFloat(R.id.imgBtnBear.toString(), 0F)}"""

            if(prefs.contains(R.id.imgBtnCat.toString()))
                activity!!.findViewById<TextView>(R.id.txtCatRating).text =
                    """${getString(R.string.yourRating)} ${prefs.getFloat(R.id.imgBtnCat.toString(), 0F)}"""

            if(prefs.contains(R.id.imgBtnDog.toString()))
                activity!!.findViewById<TextView>(R.id.txtDogRating).text =
                    """${getString(R.string.yourRating)} ${prefs.getFloat(R.id.imgBtnDog.toString(), 0F)}"""

            if(prefs.contains(R.id.imgBtnRabbit.toString()))
                activity!!.findViewById<TextView>(R.id.txtRabbitRating).text =
                    """${getString(R.string.yourRating)} ${prefs.getFloat(R.id.imgBtnRabbit.toString(), 0F)}"""
        }
    }

    private inner class ImageClickListener: View.OnClickListener{
        override fun onClick(v: View?) {
            uiModel.currentAnimalID = v!!.id
            if(v != null) {(activity as MainActivity).rateAnimal()}
        }
    }

    private inner class ButtonListener : View.OnClickListener{
        override fun onClick(v: View?) {
            uiModel.currentAnimalID = 0
            if(v != null){(activity as MainActivity).clearRatings()}
        }

    }
}