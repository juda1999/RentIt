package com.idz.rentIt.model

import android.graphics.Bitmap
import com.idz.rentIt.base.EmptyCallback
import com.idz.rentIt.base.StudentsCallback

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

    fun add(post: Post, profileImage: Bitmap?, storage: Storage, callback: EmptyCallback) {
        firebaseModel.add(post) {
            profileImage?.let {

                when (storage) {
                    Storage.FIREBASE -> {
                        uploadImageToFirebase(
                            image = it,
                            name = post.id) { url ->
                            url?.let {
                                val st = post.copy(photoUrl = it)
                                firebaseModel.add(st, callback)
                            } ?: callback()
                        }
                    }
                    Storage.CLOUDINARY -> {
                        uploadImageToCloudinary(
                            image = it,
                            name = post.id,
                            onSuccess = { url ->
                                val st = post.copy(photoUrl = url)
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