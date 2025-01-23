package com.idz.colman24class2.model

import android.graphics.Bitmap
import com.idz.colman24class2.base.EmptyCallback
import com.idz.colman24class2.base.StudentsCallback

class Model private constructor() {

    enum class Storage {
        FIREBASE,
        CLOUDINARY
    }

    private val firebaseModel = FirebaseModel()
    private val cloudinaryModel = CloudinaryModel()

    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: StudentsCallback) {
        firebaseModel.getAllStudents(callback)
    }

    fun add(student: Student, profileImage: Bitmap?, storage: Storage, callback: EmptyCallback) {
        firebaseModel.add(student) {
            profileImage?.let {

                when (storage) {
                    Storage.FIREBASE -> {
                        uploadImageToFirebase(
                            image = it,
                            name = student.id) { url ->
                            url?.let {
                                val st = student.copy(avatarUrl = it)
                                firebaseModel.add(st, callback)
                            } ?: callback()
                        }
                    }
                    Storage.CLOUDINARY -> {
                        uploadImageToCloudinary(
                            image = it,
                            name = student.id,
                            onSuccess = { url ->
                                val st = student.copy(avatarUrl = url)
                                firebaseModel.add(st, callback)
                            },
                            onError = { callback() }
                        )
                    }
                }
            } ?: callback()
        }
    }

    private fun uploadImageToFirebase(image: Bitmap, name: String, callback: (String?) -> Unit) {
        firebaseModel.uploadImage(image, name, callback)
    }

    private fun uploadImageToCloudinary(image: Bitmap, name: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        cloudinaryModel.uploadBitmap(
            bitmap = image,
//            name = name,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}