package com.idz.colman24class2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val id: String,
    val name: String,
    val avatarUrl: String,
    var isChecked: Boolean
) {

    companion object {

        private const val ID_KEY = "id"
        private const val NAME_KEY = "name"
        private const val AVATAR_URL_KEY = "avatarUrl"
        private const val IS_CHECKED_KEY = "isChecked"

        fun fromJSON(json: Map<String, Any>): Student {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val avatarUrl = json[AVATAR_URL_KEY] as? String ?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean ?: false

            return Student(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                isChecked = isChecked
            )
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ID_KEY to id,
                NAME_KEY to name,
                AVATAR_URL_KEY to avatarUrl,
                IS_CHECKED_KEY to isChecked)
        }
}
