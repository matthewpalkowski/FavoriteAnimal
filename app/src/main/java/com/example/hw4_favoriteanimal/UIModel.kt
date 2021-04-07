package com.example.hw4_favoriteanimal

import android.content.res.Configuration
import androidx.lifecycle.ViewModel

/**
 * Customized ViewModel class to manage data that should persist during the lifetime
 * of the application.
 */
class UIModel() : ViewModel() {
    var currentAnimalID: Int = 0
}