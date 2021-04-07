package com.example.hw4_favoriteanimal

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * Class to manage the behavior of the MainActivity.
 * @author Matthew Palkowski
 */
class MainActivity : AppCompatActivity() {

    private lateinit var animalsFragment: AnimalsFragment
    private lateinit var ratingFragment: RatingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animalsFragment = AnimalsFragment()
        ratingFragment = RatingFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.animalFragmentContainer, animalsFragment)
            .addToBackStack(null)
            .commit()

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.ratingFragmentContainer, ratingFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            ratingFragment.cancel()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.ratingFragmentContainer, ratingFragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    fun clearRatings(){
        getPreferences(Activity.MODE_PRIVATE).edit().clear().commit()
        animalsFragment.updateRatings()
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            ratingFragment.setDisplay()
        }
    }

    fun rateAnimal(){
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            supportFragmentManager.beginTransaction()
                .replace(R.id.animalFragmentContainer, ratingFragment)
                .addToBackStack(null)
                .commit()
        }
        else ratingFragment.setDisplay()
    }

    fun ratingSubmitted(animal: Int, rating: Float){
        val edit : SharedPreferences.Editor  = getPreferences(Activity.MODE_PRIVATE).edit()
        edit.putFloat(animal.toString(),rating)
        edit.apply()
        val msg = Toast.makeText(applicationContext, R.string.ratingSubmitted, Toast.LENGTH_SHORT)
        msg.show()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.animalFragmentContainer, animalsFragment)
                    .addToBackStack(null)
                    .commit()
        }
        else animalsFragment.updateRatings()
    }
}