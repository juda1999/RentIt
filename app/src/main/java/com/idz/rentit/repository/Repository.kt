package com.idz.rentit.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.repository.firebase.AuthModel
import com.idz.rentit.repository.firebase.FirebaseModel
import com.idz.rentit.repository.models.*
import com.idz.rentit.repository.room.LocalModel
import java.util.Objects
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Repository private constructor() {
    val localModel: LocalModel = LocalModel()
    private val firebaseModel: FirebaseModel = FirebaseModel()
    private val authModel: AuthModel = AuthModel()
    val executor: Executor = Executors.newSingleThreadExecutor()
    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var properties: LiveData<List<Property>>? = null
    private var filteredProperties: MutableLiveData<List<Property>> = MutableLiveData()
    private var priceFilter: Double? = null
    private var locationFilter: String? = null
    private var shelterFilter: Boolean? = null
    private var furnishedFilter: Boolean? = null

    fun getFirebaseModel(): FirebaseModel {
        return this.firebaseModel
    }

    fun getAuthModel(): AuthModel {
        return this.authModel
    }

    val allFilteredProperties: LiveData<List<Property>>
        get() {
            if (Objects.isNull(this.properties)) {
                this.properties = localModel.propertyHandler.allProperties
                this.properties!!.observeForever { properties ->
                    setFilteredProperties()
                }
                refreshAllProperties()
                Log.d("REPO", "Properties received: ${this.properties?.value}")
            }
            return this.filteredProperties
        }

    fun setFilteredProperties() {
        executor.execute {
            val filteredList = this.properties?.value?.filter { property ->
                val priceMatch = this.priceFilter?.let { it <= property.price } ?: true
                val locationMatch = this.locationFilter?.let { property.location.contains(it, ignoreCase = true) == true } ?: true
                val shelterMatch = this.shelterFilter?.let { it == property.hasShelter } ?: true
                val furnishedMatch = this.furnishedFilter?.let { it == property.isFurnished } ?: true

                priceMatch && locationMatch && shelterMatch && furnishedMatch
            }
            mainThreadHandler.post {
                filteredProperties.value = filteredList ?: emptyList()
            }
        }
    }

     fun refreshAllProperties() {
        NotificationManager.instance().getEventPropertyListLoadingState().value = LoadingState.LOADING
         val localLastUpdate: Long = Property.getLocalLastUpdate()
        getFirebaseModel().propertyExecutor.getAllPropertiesSinceLastUpdate(localLastUpdate) { properties ->
            executor.execute {
                Log.d("juda two", properties.size.toString())
                var propertyGlobalLastUpdate = localLastUpdate
                for (property in properties) {
                    localModel.propertyHandler.addProperty(property)
                    if (propertyGlobalLastUpdate < property.lastUpdate!!) {
                        propertyGlobalLastUpdate = property.lastUpdate!!
                    }
                }

                Property.setLocalLastUpdate(propertyGlobalLastUpdate)
                NotificationManager.instance()
                    .getEventPropertyListLoadingState().postValue(LoadingState.NOT_LOADING)
            }
        }
    }

    fun register(addUserListener: () -> Unit, user: User, password: String) {
        authModel.register(user.email, password) { uid: String ->
            user.userId = uid
            firebaseModel.userExecutor.addUser(user, addUserListener)
        }
    }

    fun applyFilter(
        price: Double?,
        location: String?,
        shelter: Boolean?,
        furnished: Boolean?
    ) {
        this.priceFilter = price
        this.locationFilter = location
        this.shelterFilter = shelter
        this.furnishedFilter = furnished
    }

    companion object {
        val repositoryInstance: Repository = Repository()
    }
}