package com.idz.rentit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property

class PropertyHomeFragmentViewModel : ViewModel() {
    private val propertyList: LiveData<List<Property>> = Repository.repositoryInstance.allProperties!!

    fun getPropertyList(): LiveData<List<Property>> {
        return propertyList
    }
}

