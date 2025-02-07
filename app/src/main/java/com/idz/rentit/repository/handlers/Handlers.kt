package com.example.movieshare.repository.room.handlers

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import com.idz.rentit.listeners.authentication.GetPropertyItemListListener
import com.idz.rentit.listeners.authentication.GetPropertyItemListener
import com.idz.rentit.repository.models.Property
import com.idz.rentit.repository.room.localdb.AppLocalDB
import com.idz.rentit.repository.room.localdb.AppLocalDbRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PropertyHandler private constructor() {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val localDB: AppLocalDbRepository = AppLocalDB.appDB

    val allProperties: LiveData<List<Property>>?
        get() = localDB.propertyDao()?.allProperties

    fun getAllPropertiesByFilter(filter: String, listener: GetPropertyItemListListener<Property?>) {
        executor.execute {
//            val properties: List<Property> =
//                localDB.propertyDao().getAllPropertiesByFilter(filter)
//            mainThreadHandler.post { listener.onComplete(properties) }
        }
    }

    fun getMovieByName(name: String, listener: GetPropertyItemListener<Property?>) {
        executor.execute {
            val property: Property? = localDB.propertyDao()?.getPropertyByName(name)
            mainThreadHandler.post { listener.onComplete(property) }
        }
    }

    fun addProperty(property: Property) {
        try {
//            localDB.propertyDao().insertAll(property)
        } catch (e: Exception) {
            Log.d("TAG", e.message!!)
        }
    }

    companion object {
        private val movieHandlerInstance = PropertyHandler()
        fun instance(): PropertyHandler {
            return movieHandlerInstance
        }
    }
}