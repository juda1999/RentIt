package com.idz.rentit.repository.room

import com.idz.rentit.repository.handlers.PropertyHandler

class LocalModel {
    val propertyHandler: PropertyHandler
        get() = PropertyHandler.instance()

}