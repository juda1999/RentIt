package com.idz.rentit.repository.firebase.executors

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.idz.rentit.constants.UserConstants
import com.idz.rentit.repository.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class UserExecutor private constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getUserById(id: String?, listener: (User) -> Unit) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .whereEqualTo(FieldPath.documentId(), id)
            .get()
            .addOnSuccessListener { task: QuerySnapshot ->
                var user: User? = null
                val jsonDocument = task.documents
                if (jsonDocument.isNotEmpty()) {
                    user = jsonDocument[0].data?.let { User.fromJson(it) }
                }
                if (user != null) {
                    listener(user)
                }
            }
    }

    fun addUser(user: User, listener: () -> Unit) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .document(user.userId)
            .set(user.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener() }
    }

    fun updateUser(user: User, listener: () -> Unit) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .document(user.userId)
            .set(user.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener() }
    }

    fun uploadUserImage(imageBmp: Bitmap, name: String, listener: (String?) -> Unit) {
            val imagesRef = storage.reference.child(IMAGE_FOLDER).child(name)

            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

            val uploadTask = imagesRef.putBytes(data)
            uploadTask.addOnFailureListener { exception: Exception? ->
                listener(null)
                Log.e("StorageException", "Upload failed: ${exception?.message}")
            }
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    imagesRef.downloadUrl
                        .addOnFailureListener { uri: Exception? -> listener(null) }
                        .addOnSuccessListener { uri: Uri -> listener(uri.toString()) }
                }
        }

    companion object {
        private val userExecutorInstance = UserExecutor()
        val IMAGE_FOLDER: String = "users"
        fun instance(): UserExecutor {
            return userExecutorInstance
        }
    }
}