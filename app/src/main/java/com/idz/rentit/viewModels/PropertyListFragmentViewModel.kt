package com.idz.rentit.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property


class PropertyListFragmentViewModel: ViewModel() {
    private val propertyList: LiveData<List<Property>>? = Repository.repositoryInstance.allProperties

    fun getPropertyList(): LiveData<List<Property>>? {
        return this.propertyList
    }
}