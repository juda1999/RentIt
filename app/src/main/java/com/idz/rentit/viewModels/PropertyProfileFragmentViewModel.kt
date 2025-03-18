package com.idz.rentit.viewModels

import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.models.Property

class PropertyProfileFragmentViewModel: ViewModel() {
    private var property: Property? = null

    fun getProperty(): Property? {
        return property
    }

    fun setProperty(user: Property) {
        this.property = user
    }
}