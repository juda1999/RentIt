package com.idz.rentit.repository.firebase.executors

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.idz.rentit.constants.PropertyConstants
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class PropertyExecutor private constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getAllPropertiesSinceLastUpdate(
        localLastUpdate: Long,
        listener: (List<Property>) -> Unit
    ) {
        db.collection(PropertyConstants.PROPERTY_COLLECTION_NAME)
            .whereGreaterThanOrEqualTo(
                PropertyConstants.LAST_UPDATE,
                Timestamp(localLastUpdate, 0)
            )
            .get()
            .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { task: QuerySnapshot ->
                val properties: MutableList<Property> = ArrayList()
                for (document in task.documents) {
                    val property: Property = Property.fromJson(document.data!!)
                    property.propertyId = (document.id)
                    properties.add(property)
                }
                listener(properties)
            }).addOnFailureListener(OnFailureListener { task: java.lang.Exception ->
                Log.d(
                    "Error",
                    task.message!!
                )
            })
    }

    fun getProperties(listener: (List<Property>) -> Unit) {
        db.collection(PropertyConstants.PROPERTY_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { task ->
                val properties = mutableListOf<Property>()
                for (document in task.documents) {
                    Log.d("TAG", "Document: " + document.data)
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

    fun deleteProperty(id: String, listener: () -> Unit) {
        db.collection(PropertyConstants.PROPERTY_COLLECTION_NAME)
            .document(id)
            .delete()
            .addOnCompleteListener { task: Task<Void?>? -> listener() }
    }

    fun uploadPropertyImage(imageBmp: Bitmap, name: String,  context: Context, listener: (String?) -> Unit) {
        val file = File(context.filesDir, name)
        FileOutputStream(file).use { imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        listener(file.absolutePath)
//        val imagesRef = storage.reference.child(IMAGE_FOLDER).child(name)
//
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val data = byteArrayOutputStream.toByteArray()
//
//        val uploadTask = imagesRef.putBytes(data)
//        uploadTask.addOnFailureListener { exception: Exception? ->
//            listener(null)
//        }
//            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
//                imagesRef.downloadUrl
//                    .addOnFailureListener { uri: Exception? -> listener(null) }
//                    .addOnSuccessListener { uri: Uri -> listener(uri.toString()) }
//            }
    }

    companion object {
        private val propertyExecutorInstance = PropertyExecutor()
        const val IMAGE_FOLDER: String = "properties"
        fun instance(): PropertyExecutor {
            return propertyExecutorInstance
        }
    }
}