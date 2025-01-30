package com.idz.rentIt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: String,
    val address: String,
    val photoUrl: String,
    val price: Int,
    var isFurnished: Boolean,
    var hasShelter: Boolean
) {

    companion object {
        private const val ID_KEY = "id"
        private const val ADDRESS_KEY = "address"
        private const val PHOTO_URL_KEY = "photoUrl"
        private const val PRICE_KEY = "price"
        private const val IS_FURNISHED_KEY = "isFurnished"
        private const val HAS_SHELTER_KEY = "hasShelter"


        fun fromJSON(json: Map<String, Any>): Post {
            val id = json[ID_KEY] as? String ?: ""
            val address = json[ADDRESS_KEY] as? String ?: ""
            val photoUrl = json[PHOTO_URL_KEY] as? String ?: ""
            val price = json[PRICE_KEY] as? Int ?: 0
            val isFurnished = json[IS_FURNISHED_KEY] as? Boolean ?: false
            val hasShelter = json[HAS_SHELTER_KEY] as? Boolean ?: false

            return Post(
                id = id,
                address = address,
                photoUrl = photoUrl,
                price = price,
                isFurnished = isFurnished,
                hasShelter = hasShelter
            )
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ID_KEY to id,
                ADDRESS_KEY to address,
                PHOTO_URL_KEY to photoUrl,
                PRICE_KEY to price,
                IS_FURNISHED_KEY to isFurnished,
                HAS_SHELTER_KEY to hasShelter)
        }
}
