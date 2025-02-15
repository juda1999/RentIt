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
import com.idz.rentit.constants.PropertyConstants.OWNER_ID
import com.idz.rentit.constants.PropertyConstants.PRICE
import com.idz.rentit.constants.PropertyConstants.PROPERTY_ID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class Property {
    @PrimaryKey
    private lateinit var propertyId: String

    @ColumnInfo(index = true)
    var location: String
    var numberOfRooms: Int
    var price: Int
    var description: String
    var ownerId: String
    var lastUpdate: Long? = null
    private var imageUrl: String

    constructor(
        location: String, numberOfRooms: Int,
        price: Int, description: String, ownerId: String, imageUrl: String
    ) {
        this.location = location
        this.numberOfRooms = numberOfRooms
        this.price = price
        this.description = description
        this.ownerId = ownerId
        this.imageUrl = imageUrl
    }

    @Ignore
    constructor(
        propertyId: String, location: String,
        numberOfRooms: Int, price: Int, description: String, ownerId: String, imageUrl: String
    ) {
        this.propertyId = propertyId
        this.location = location
        this.numberOfRooms = numberOfRooms
        this.price = price
        this.description = description
        this.ownerId = ownerId
        this.imageUrl = imageUrl
    }

    fun toJson(): Map<String, Any> {
        val propertyJSON: MutableMap<String, Any> = HashMap()
        propertyJSON[PROPERTY_ID] = this.propertyId
        propertyJSON[LOCATION] = location
        propertyJSON[NUMBER_OF_ROOMS] = numberOfRooms
        propertyJSON[PRICE] = price
        propertyJSON[DESCRIPTION] = description
        propertyJSON[OWNER_ID] = ownerId
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
            val ownerId = json[OWNER_ID].toString()
            val imageUrl = json[IMAGE_URL].toString()

            val property = Property(propertyId, location, numberOfRooms, price, description, ownerId, imageUrl)
            val lastUpdate = json[LAST_UPDATE] as Timestamp?
            property.lastUpdate = lastUpdate!!.seconds
            return property
        }
    }
}