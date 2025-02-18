package com.idz.rentit.repository.firebase

import com.example.movieshare.repository.firebase.executors.PropertyExecutor
import com.example.movieshare.repository.firebase.executors.UserExecutor
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

//    val movieCategoryExecutor: MovieCategoryExecutor
//        get() = MovieCategoryExecutor.instance()
//
//
//    val movieCommentExecutor: MovieCommentExecutor
//        get() = MovieCommentExecutor.instance()

    val propertyExecutor: PropertyExecutor
        get() = PropertyExecutor.instance();

    val userExecutor: UserExecutor
        get() = UserExecutor.instance()
}