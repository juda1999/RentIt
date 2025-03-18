package com.idz.rentit.repository.firebase

import com.idz.rentit.repository.firebase.executors.PropertyExecutor
import com.idz.rentit.repository.firebase.executors.UserExecutor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebaseModel {
    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
        val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = settings
    }

    val propertyExecutor: PropertyExecutor
        get() = PropertyExecutor.instance();

    val userExecutor: UserExecutor
        get() = UserExecutor.instance()
}