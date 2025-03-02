package com.idz.rentit.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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
import com.idz.rentit.context.MyApplication
import android.content.Context;
import com.idz.rentit.constants.PropertyConstants.HAS_SHELTER
import com.idz.rentit.constants.PropertyConstants.IS_FURNISHED
import com.idz.rentit.constants.PropertyConstants.PROPERTY_LOCAL_LAST_UPDATE

@Entity(
    tableName = "property",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["userId"],
//            childColumns = ["userId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class Property(
    @PrimaryKey
    var propertyId: String,

    @ColumnInfo(index = true)
    var location: String,
    var numberOfRooms: Long,
    var price: Long,
    var description: String,
    @ColumnInfo(index = true)
    var userId: String,
    var imageUrl: String,
    var hasShelter: Boolean = false,
    var isFurnished: Boolean = false,
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
        propertyJSON[HAS_SHELTER] = hasShelter
        propertyJSON[IS_FURNISHED] = isFurnished
        return propertyJSON
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): Property {
            val propertyId = json[PROPERTY_ID].toString()
            val location = json[LOCATION].toString()
            val numberOfRooms = json[NUMBER_OF_ROOMS] as Long
            val price = json[PRICE] as Long
            val description = json[DESCRIPTION].toString()
            val userId = json[UserConstants.USER_ID].toString()
            val imageUrl = json[IMAGE_URL].toString()
            val hasShelter = json[HAS_SHELTER] as Boolean
            val isFurnished = json[IS_FURNISHED] as Boolean

            val property = Property(propertyId, location, numberOfRooms, price, description, userId, imageUrl, hasShelter, isFurnished)
            val lastUpdate = json[LAST_UPDATE] as Timestamp?
            property.lastUpdate = lastUpdate!!.seconds
            return property
        }

    fun getLocalLastUpdate(): Long {
        return MyApplication.Globals.appContext!!.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            .getLong(PROPERTY_LOCAL_LAST_UPDATE, 0)
    }

    fun setLocalLastUpdate(localLastUpdate: Long) {
        MyApplication.Globals.appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.edit()
            ?.putLong(PROPERTY_LOCAL_LAST_UPDATE, localLastUpdate)?.apply()
    }
    }
}
