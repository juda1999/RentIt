package com.idz.rentit.listeners.authentication

interface GetPropertyItemListener<T> {
    fun onComplete(movieItem: T)
}

interface GetPropertyItemListListener<T> {
    fun onComplete(movieItemList: List<T>?)
}

interface ExecutePropertyItemListener {
    fun onComplete()
}