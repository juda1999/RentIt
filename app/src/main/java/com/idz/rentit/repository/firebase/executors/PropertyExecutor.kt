package com.example.movieshare.repository.firebase.executors

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.idz.rentit.constants.UserConstants
import com.idz.rentit.listeners.authentication.*
import com.idz.rentit.repository.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.idz.rentit.constants.PropertyConstants
import com.idz.rentit.repository.models.Property
import java.io.ByteArrayOutputStream

class PropertyExecutor private constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getProperties(listener: (properties: MutableList<Property>) -> Unit) {
        db.collection(PropertyConstants.PROPERTY_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { task ->
                val properties = mutableListOf<Property>()
                for (document in task.documents) {
                    val property = Property.fromJson(document.data!!)
                    property.propertyId = document.id
                    properties.add(property)
                }
                listener(properties)
            }
            .addOnFailureListener { exception ->
                Log.d("Error", exception.message.orEmpty())
            }
    }

    fun addProperty(property: Property, listener: () -> Unit) {
        db.collection(PropertyConstants.PROPERTY_COLLECTION_NAME)
            .document(property.propertyId)
            .set(property.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener() }
    }

    fun updateUser(user: User, listener: UpdateUserListener) {
        db.collection(UserConstants.USER_COLLECTION_NAME)
            .document(user.userId)
            .set(user.toJson())
            .addOnCompleteListener { task: Task<Void?>? -> listener.onComplete() }
    }

    fun uploadPropertyImage(imageBmp: Bitmap, name: String, listener: (String?) -> Unit) {
        val imagesRef = storage.reference.child(IMAGE_FOLDER).child(name)

        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()

        val uploadTask = imagesRef.putBytes(data)
        uploadTask.addOnFailureListener { exception: Exception? ->
            listener(null)
        }
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                imagesRef.downloadUrl
                    .addOnFailureListener { uri: Exception? -> listener(null) }
                    .addOnSuccessListener { uri: Uri -> listener(uri.toString()) }
            }
    }

    companion object {
        private val propertyExecutorInstance = PropertyExecutor()
        const val IMAGE_FOLDER: String = "properties"
        fun instance(): PropertyExecutor {
            return propertyExecutorInstance
        }
    }
}