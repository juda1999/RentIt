package com.idz.rentit.repository.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.idz.rentit.constants.UserConstants.USER_EMAIL
import com.idz.rentit.constants.UserConstants.USER_FIRST_NAME
import com.idz.rentit.constants.UserConstants.USER_ID
import com.idz.rentit.constants.UserConstants.USER_IMAGE_URL
import com.idz.rentit.constants.UserConstants.USER_LAST_NAME
import com.idz.rentit.constants.UserConstants.USER_LAST_UPDATE
import java.util.Objects

@Entity
class User {
    @PrimaryKey
    private var userId: String? = null
    var firstName: String
    var lastName: String
    var email: String
    var imageUrl: String? = null
    var userLastUpdate: Long? = null

    constructor(firstName: String, lastName: String, email: String) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    @Ignore
    constructor(
        userId: String, firstName: String, lastName: String,
        email: String, imageUrl: String?
    ) {
        this.userId = userId
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.imageUrl = imageUrl
    }

    fun toJson(): Map<String, Any?> {
        val userJson: MutableMap<String, Any?> = HashMap()
        userJson[USER_ID] = getUserId()
        userJson[USER_FIRST_NAME] = firstName
        userJson[USER_LAST_NAME] = lastName
        userJson[USER_EMAIL] = email
        userJson[USER_IMAGE_URL] = imageUrl
        userJson[USER_LAST_UPDATE] = FieldValue.serverTimestamp()
        return userJson
    }

    fun getUserId(): String {
        return userId!!
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): User {
            val userId = json[USER_ID].toString()
            val firstName = json[USER_FIRST_NAME].toString()
            val lastName = json[USER_LAST_NAME].toString()
            val email = json[USER_EMAIL].toString()
            var imageUrl: String? = null
            if (Objects.nonNull(json[USER_IMAGE_URL])) {
                imageUrl = json[USER_IMAGE_URL].toString()
            }
            val user = User(userId, firstName, lastName, email, imageUrl)
            val lastUpdate = json[USER_LAST_UPDATE] as Timestamp?
            user.userLastUpdate = lastUpdate!!.seconds
            return user
        }
    }
}