package com.idz.rentit.notifications

import androidx.lifecycle.MutableLiveData
import com.idz.rentit.enums.LoadingState

class NotificationManager private constructor() {
    private val eventPropertyListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.NOT_LOADING)
    companion object {
        private val notificationManagerInstance = NotificationManager()

        @JvmStatic
        fun instance(): NotificationManager {
            return notificationManagerInstance
        }
    }

    fun getEventPropertyListLoadingState(): MutableLiveData<LoadingState> {
        return eventPropertyListLoadingState
    }
}
