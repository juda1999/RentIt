package com.idz.rentit.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.idz.rentit.constants.PropertyConstants.DESCRIPTION
import com.idz.rentit.constants.PropertyConstants.IMAGE_URL
import com.idz.rentit.constants.PropertyConstants.LAST_UPDATE
import com.idz.rentit.constants.PropertyConstants.LOCATION
import com.idz.rentit.constants.PropertyConstants.NUMBER_OF_ROOMS
import com.idz.rentit.constants.PropertyConstants.PRICE
import com.idz.rentit.constants.PropertyConstants.PROPERTY_ID
import com.idz.rentit.constants.UserConstants

@Entity(
    tableName = "property",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Property(
    @PrimaryKey
    var propertyId: String,

    @ColumnInfo(index = true)
    var location: String,
    var numberOfRooms: Int,
    var price: Int,
    var description: String,
    @ColumnInfo(index = true)
    var userId: String,
    var imageUrl: String,
    var lastUpdate: Long? = null,
) {
    fun toJson(): Map<String, Any> {
        val propertyJSON: MutableMap<String, Any> = HashMap()
        propertyJSON[PROPERTY_ID] = this.propertyId
        propertyJSON[LOCATION] = location
        propertyJSON[NUMBER_OF_ROOMS] = numberOfRooms
        propertyJSON[PRICE] = price
        propertyJSON[DESCRIPTION] = description
        propertyJSON[UserConstants.USER_ID] = userId
        propertyJSON[LAST_UPDATE] = FieldValue.serverTimestamp()
        propertyJSON[IMAGE_URL] = imageUrl
        return propertyJSON
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): Property {
            val propertyId = json[PROPERTY_ID].toString()
            val location = json[LOCATION].toString()
            val numberOfRooms = json[NUMBER_OF_ROOMS] as Int
            val price = json[PRICE] as Int
            val description = json[DESCRIPTION].toString()
            val userId = json[UserConstants.USER_ID].toString()
            val imageUrl = json[IMAGE_URL].toString()

            val property = Property(propertyId, location, numberOfRooms, price, description, userId, imageUrl)
            val lastUpdate = json[LAST_UPDATE] as Timestamp?
            property.lastUpdate = lastUpdate!!.seconds
            return property
        }
    }
}