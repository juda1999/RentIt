package com.idz.colman24class2.model

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.storage.storage
import com.idz.colman24class2.base.Constants
import com.idz.colman24class2.base.EmptyCallback
import com.idz.colman24class2.base.StudentsCallback
import java.io.ByteArrayOutputStream

class FirebaseModel {

    private val database = Firebase.firestore
    private val storage = Firebase.storage

    init {
        val setting = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }

        database.firestoreSettings = setting
    }

    fun getAllStudents(callback: StudentsCallback) {
        database.collection(Constants.COLLECTIONS.STUDENTS).get()
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val students: MutableList<Student> = mutableListOf()
                        for (json in it.result) {
                            students.add(Student.fromJSON(json.data))
                        }
                        callback(students)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun add(student: Student, callback: EmptyCallback) {
        database.collection(Constants.COLLECTIONS.STUDENTS).document(student.id)
            .set(student.json)
            .addOnCompleteListener {
                callback()
            }
    }

    fun uploadImage(image: Bitmap, name: String, callback: (String?) -> Unit) {
        val storageRef = storage.reference
        val imageProfileRef = storageRef.child("images/$name.jpg")
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = imageProfileRef.putBytes(data)
        uploadTask
            .addOnFailureListener { callback(null) }
            .addOnSuccessListener { taskSnapshot ->
            imageProfileRef.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }
        }
    }
}