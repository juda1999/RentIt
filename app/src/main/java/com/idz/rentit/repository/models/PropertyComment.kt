package com.idz.rentit.repository.models

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_DESCRIPTION
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_ID
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_LAST_UPDATE
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_LOCAL_LAST_UPDATE
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_MOVIE_ID
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_MOVIE_NAME
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_RATING
import com.idz.rentit.constants.PropertyCommentConstants.MOVIE_COMMENT_USER_ID
import com.idz.rentit.context.MyApplication
import com.idz.rentit.repository.models.Property

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = Property::class,
//        parentColumns = "movieId",
//        childColumns = "movieId",
//        onDelete = CASCADE
//    )]
)
class PropertyComment {
    @PrimaryKey
    private lateinit var movieCommentId: String

    var userId: String

    @ColumnInfo(index = true)
    var movieId: String

    var movieName: String
    var movieRatingOfComment: String
    var description: String
    var movieCommentLastUpdate: Long? = null

    constructor(
        userId: String, movieId: String,
        movieName: String, movieRatingOfComment: String, description: String
    ) {
        this.userId = userId
        this.movieId = movieId
        this.movieName = movieName
        this.movieRatingOfComment = movieRatingOfComment
        this.description = description
    }

    @Ignore
    constructor(
        movieCommentId: String, userId: String, movieId: String,
        movieName: String, movieRatingOfComment: String, description: String
    ) {
        this.movieCommentId = movieCommentId
        this.userId = userId
        this.movieId = movieId
        this.movieName = movieName
        this.movieRatingOfComment = movieRatingOfComment
        this.description = description
    }

    fun toJson(): Map<String, Any> {
        val movieCommentJson: MutableMap<String, Any> = HashMap()
        movieCommentJson[MOVIE_COMMENT_ID] = getMovieCommentId()
        movieCommentJson[MOVIE_COMMENT_USER_ID] = userId
        movieCommentJson[MOVIE_COMMENT_MOVIE_ID] = movieId
        movieCommentJson[MOVIE_COMMENT_MOVIE_NAME] = movieName
        movieCommentJson[MOVIE_COMMENT_RATING] = movieRatingOfComment
        movieCommentJson[MOVIE_COMMENT_DESCRIPTION] = description
        movieCommentJson[MOVIE_COMMENT_LAST_UPDATE] = FieldValue.serverTimestamp()
        return movieCommentJson
    }

    fun getMovieCommentId(): String {
        return movieCommentId!!
    }

    fun setMovieCommentId(movieCommentId: String) {
        this.movieCommentId = movieCommentId
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): PropertyComment {
            val movieCommentId = json[MOVIE_COMMENT_ID].toString()
            val userId = json[MOVIE_COMMENT_USER_ID].toString()
            val movieId = json[MOVIE_COMMENT_MOVIE_ID].toString()
            val movieName = json[MOVIE_COMMENT_MOVIE_NAME].toString()
            val movieRatingOfComment = json[MOVIE_COMMENT_RATING].toString()
            val description = json[MOVIE_COMMENT_DESCRIPTION].toString()
            val movieComment = PropertyComment(
                movieCommentId,
                userId,
                movieId,
                movieName,
                movieRatingOfComment,
                description
            )
            val lastUpdate = json[MOVIE_COMMENT_LAST_UPDATE] as Timestamp?
            movieComment.movieCommentLastUpdate = lastUpdate!!.seconds
            return movieComment
        }

//        var localLastUpdate: Long?
//            get() = MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                .getLong(MOVIE_COMMENT_LOCAL_LAST_UPDATE, 0)
//            set(localLastUpdate) {
//                MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                    .edit().putLong(MOVIE_COMMENT_LOCAL_LAST_UPDATE, localLastUpdate).commit()
//            }
    }
}