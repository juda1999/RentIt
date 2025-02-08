package com.example.movieshare.repository.firebase.executors

import android.graphics.Bitmap
import android.net.Uri
import com.idz.rentit.constants.UserConstants
import com.idz.rentit.listeners.authentication.*
//import com.idz.rentit.listeners.movies.GetMovieItemListener
import com.idz.rentit.repository.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class UserExecutor private constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getUserById(id: String?, listener: GetMovieItemListener<User?>) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .whereEqualTo(FieldPath.documentId(), id)
            .get()
            .addOnSuccessListener { task: QuerySnapshot ->
                var user: User? = null
                val jsonDocument = task.documents
                if (!jsonDocument.isEmpty()) {
                    user = User.fromJson(jsonDocument[0].data)
                }
                listener.onComplete(user)
            }
    }

    fun addUser(user: User, listener: AddUserListener) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .document(user.getUserId())
            .set(user.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener.onComplete() }
    }

    fun updateUser(user: User, listener: UpdateUserListener) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .document(user.getUserId())
            .set(user.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener.onComplete() }
    }

    fun uploadUserImage(imageBmp: Bitmap, name: String, listener: UploadUserImageListener) {
        val imagesRef = storage.reference.child(IMAGE_FOLDER).child(name)

        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()

        val uploadTask = imagesRef.putBytes(data)
        uploadTask.addOnFailureListener { exception: Exception? ->
            listener.onComplete(
                null
            )
        }
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                imagesRef.downloadUrl
                    .addOnFailureListener { uri: Exception? -> listener.onComplete(null) }
                    .addOnSuccessListener { uri: Uri -> listener.onComplete(uri.toString()) }
            }
    }

    companion object {
        private val userExecutorInstance = UserExecutor()
        const val IMAGE_FOLDER: String = "users"
        fun instance(): UserExecutor {
            return userExecutorInstance
        }
    }
}