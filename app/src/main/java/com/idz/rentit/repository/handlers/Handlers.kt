package com.idz.rentit.repository.handlers

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import com.idz.rentit.listeners.authentication.GetPropertyItemListListener
import com.idz.rentit.repository.models.Property
import com.idz.rentit.repository.room.database.AppLocalDB
import com.idz.rentit.repository.room.database.AppLocalDbRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PropertyHandler private constructor() {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val localDB: AppLocalDbRepository = AppLocalDB.getAppDB

    val allProperties: LiveData<List<Property>>
        get() = localDB.propertyDao().getAllProperties()

    fun getAllPropertiesByFilter(filter: String, listener: GetPropertyItemListListener<Property?>) {
        executor.execute {
            val properties: List<Property> =
                localDB.propertyDao().getAllPropertiesByFilter(filter)
            mainThreadHandler.post { listener.onComplete(properties) }
        }
    }

    fun addProperty(property: Property) {
        try {
            localDB.propertyDao().insertAll(property)
        } catch (e: Exception) {
            Log.d("ERROR ADDING PROPERTY", e.message!!)
        }
    }

    companion object {
        private val propertyHandlerInstance = PropertyHandler()
        fun instance(): PropertyHandler {
            return propertyHandlerInstance
        }
    }
}