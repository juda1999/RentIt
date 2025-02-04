package com.idz.rentit.repository.firebase

//import com.idz.rentit.repository.firebase.executors.*
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
//    val movieExecutor: MovieExecutor
//        get() = MovieExecutor.instance()
//
//    val movieCommentExecutor: MovieCommentExecutor
//        get() = MovieCommentExecutor.instance()
//
//    val userExecutor: UserExecutor
//        get() = UserExecutor.instance()
}