package com.idz.rentit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import androidx.lifecycle.map

class UserPropertiesFragmentViewModel: ViewModel() {
    private val propertyList: LiveData<List<Property>> =
        Repository.repositoryInstance.allFilteredProperties

    fun getPropertyList(userId: String): LiveData<List<Property>> {
        return propertyList.map { properties -> properties.filter { it.userId == userId } }

//        return Transformations.map(propertyList) { properties: List<Property> ->
//            properties.filter { it.userId == userId }
//        }
    }
}