package com.idz.rentit.repository.handlers

import android.util.Log
import androidx.activity.result.launch
import androidx.lifecycle.LiveData
import com.idz.rentit.repository.models.Property
import com.idz.rentit.repository.room.database.AppLocalDB
import com.idz.rentit.repository.room.database.AppLocalDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyHandler private constructor() {
    private val localDB: AppLocalDbRepository = AppLocalDB.getAppDB

    val allProperties: LiveData<List<Property>>
        get() = localDB.propertyDao().getAllProperties()

    fun addProperty(property: Property) {
        try {
            localDB.propertyDao().insertAll(property)
        } catch (e: Exception) {
            Log.d("ERROR ADDING PROPERTY", e.message!!)
        }
    }

    fun deleteProperty(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Juda", "Attempting to delete property with ID: $id")
                localDB.propertyDao().deletePropertyById(id)
                Log.d("Juda", "Property with ID: $id deleted successfully")
            } catch (e: Exception) {
                Log.e("Juda", "Error deleting property with ID: $id", e)
            }
        }
    }

    companion object {
        private val propertyHandlerInstance = PropertyHandler()
        fun instance(): PropertyHandler {
            return propertyHandlerInstance
        }
    }
}