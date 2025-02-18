package com.idz.rentit.notifications

import androidx.lifecycle.MutableLiveData
import com.idz.rentit.enums.LoadingState

class NotificationManager private constructor() {

    private val eventMovieCategoryListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.NOT_LOADING)
    private val eventMovieListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.NOT_LOADING)
    private val eventMovieCommentListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.NOT_LOADING)

    companion object {
        private val notificationManagerInstance = NotificationManager()

        @JvmStatic
        fun instance(): NotificationManager {
            return notificationManagerInstance
        }
    }

    fun getEventMovieCategoryListLoadingState(): MutableLiveData<LoadingState> {
        return eventMovieCategoryListLoadingState
    }

    fun getEventPropertyListLoadingState(): MutableLiveData<LoadingState> {
        return eventMovieListLoadingState
    }

    fun getEventPropertyCommentListLoadingState(): MutableLiveData<LoadingState> {
        return eventMovieCommentListLoadingState
    }
}
