package com.idz.rentit.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.idz.rentit.constants.PropertyCommentConstants.COMMENT_ID
import com.idz.rentit.constants.PropertyCommentConstants.COMMENT_TEXT
import com.idz.rentit.constants.PropertyCommentConstants.UPDATE_TIME
import com.idz.rentit.constants.PropertyCommentConstants.USER_ID
import com.idz.rentit.constants.PropertyConstants

@Entity(
   foreignKeys = [
        ForeignKey(
            entity = Property::class,
            parentColumns = [PropertyConstants.PROPERTY_ID],
            childColumns = [PropertyConstants.PROPERTY_ID],
            onDelete = ForeignKey.CASCADE)]
)
class PropertyComment {
    @PrimaryKey
    private lateinit var commentId: String

    var userId: String

    @ColumnInfo(index = true)
    var propertyId: String

    var commentText: String
    var updateTime: Long? = null

    constructor(
        userId: String, propertyId: String, commentText: String
    ) {
        this.userId = userId
        this.propertyId = propertyId
        this.commentText = commentText
    }

    @Ignore
    constructor(
        commentId: String, userId: String, propertyId: String, commentText: String
    ) {
        this.commentId = commentId
        this.userId = userId
        this.propertyId = propertyId
        this.commentText = commentText
    }

    fun toJson(): Map<String, Any> {
        val movieCommentJson: MutableMap<String, Any> = HashMap()
        movieCommentJson[COMMENT_ID] = commentId
        movieCommentJson[USER_ID] = userId
        movieCommentJson[PropertyConstants.PROPERTY_ID] = propertyId
        movieCommentJson[COMMENT_TEXT] = commentText
        movieCommentJson[UPDATE_TIME] = FieldValue.serverTimestamp()
        return movieCommentJson
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): PropertyComment {
            val commentId = json[COMMENT_ID].toString()
            val userId = json[USER_ID].toString()
            val propertyId = json[PropertyConstants.PROPERTY_ID].toString()
            val commentText = json[COMMENT_TEXT].toString()
            val comment = PropertyComment(
                commentId,
                userId,
                propertyId,
                commentText
            )
            val lastUpdate = json[UPDATE_TIME] as Timestamp?
            comment.updateTime = lastUpdate!!.seconds
            return comment
        }
    }
}