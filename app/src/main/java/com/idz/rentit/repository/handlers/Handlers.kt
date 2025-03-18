package com.idz.rentit.repository.handlers

import android.util.Log
import androidx.lifecycle.LiveData
import com.idz.rentit.repository.models.Property
import com.idz.rentit.repository.room.database.AppLocalDB
import com.idz.rentit.repository.room.database.AppLocalDbRepository

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
        try {
//            localDB.propertyDao().deletePropertyById(id)
        } catch (e: Exception) {
            Log.d("ERROR Deleting property", e.message!!)
        }
    }

    companion object {
        private val propertyHandlerInstance = PropertyHandler()
        fun instance(): PropertyHandler {
            return propertyHandlerInstance
        }
    }
}