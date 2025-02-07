package com.idz.rentit.repository.models

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.idz.rentit.constants.PropertyConstants.MOVIE_CATEGORY_ID
import com.idz.rentit.constants.PropertyConstants.MOVIE_DESCRIPTION
import com.idz.rentit.constants.PropertyConstants.MOVIE_ID
import com.idz.rentit.constants.PropertyConstants.MOVIE_IMAGE_BASE_URL
import com.idz.rentit.constants.PropertyConstants.MOVIE_IMAGE_URL
import com.idz.rentit.constants.PropertyConstants.MOVIE_LAST_UPDATE
import com.idz.rentit.constants.PropertyConstants.MOVIE_LOCAL_LAST_UPDATE
import com.idz.rentit.constants.PropertyConstants.MOVIE_NAME
import com.idz.rentit.constants.PropertyConstants.MOVIE_RATING
import com.idz.rentit.context.MyApplication;


@Entity(
//    foreignKeys = [ForeignKey(
//        entity = MovieCategory::class,
//        parentColumns = "categoryId",
//        childColumns = "movieCategoryId",
//        onDelete = CASCADE,
//    )]
)
class Property {
    @PrimaryKey
    private lateinit var movieId: String

    @ColumnInfo(index = true)
    var movieCategoryId: String
    var movieName: String
    var movieRating: String
    var description: String
    var movieLastUpdate: Long? = null
    private var imageUrl: String

    constructor(
        movieCategoryId: String, movieName: String,
        movieRating: String, description: String, imageUrl: String
    ) {
        this.movieCategoryId = movieCategoryId
        this.movieName = movieName
        this.movieRating = movieRating
        this.description = description
        this.imageUrl = imageUrl
    }

    @Ignore
    constructor(
        movieId: String, movieCategoryId: String,
        movieName: String, movieRating: String, description: String, imageUrl: String
    ) {
        this.movieId = movieId
        this.movieCategoryId = movieCategoryId
        this.movieName = movieName
        this.movieRating = movieRating
        this.description = description
        this.imageUrl = imageUrl
    }

    fun toJson(): Map<String, Any> {
        val propertyJSON: MutableMap<String, Any> = HashMap()
        propertyJSON[MOVIE_ID] = getMovieId()
        propertyJSON[MOVIE_CATEGORY_ID] = movieCategoryId
        propertyJSON[MOVIE_NAME] = movieName
        propertyJSON[MOVIE_RATING] = movieRating
        propertyJSON[MOVIE_DESCRIPTION] = description
        propertyJSON[MOVIE_LAST_UPDATE] = FieldValue.serverTimestamp()
        propertyJSON[MOVIE_IMAGE_URL] = getImageUrl()
        return propertyJSON
    }

    fun getMovieId(): String {
        return movieId!!
    }

    fun setMovieId(movieId: String) {
        this.movieId = movieId
    }

    fun getImageUrl(): String {
        return getImageUrl(false)
    }

    fun getImageUrl(fullPath: Boolean): String {
        return if (fullPath) {
            MOVIE_IMAGE_BASE_URL + this.imageUrl
        } else {
            imageUrl
        }
    }

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }

    companion object {
        fun fromJson(json: Map<String?, Any>): Property {
            val movieId = json[MOVIE_ID].toString()
            val movieCategoryId = json[MOVIE_CATEGORY_ID].toString()
            val movieName = json[MOVIE_NAME].toString()
            val movieRating = json[MOVIE_RATING].toString()
            val description = json[MOVIE_DESCRIPTION].toString()
            val imageUrl = json[MOVIE_IMAGE_URL].toString()
            val movie =
                Property(movieId, movieCategoryId, movieName, movieRating, description, imageUrl)
            val lastUpdate = json[MOVIE_LAST_UPDATE] as Timestamp?
            movie.movieLastUpdate = lastUpdate!!.seconds
            return movie
        }

//        var localLastUpdate: Long?
//            get() = MyApplication.getappContextAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                .getLong(MOVIE_LOCAL_LAST_UPDATE, 0)
//            set(localLastUpdate) {
//                MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                    .edit().putLong(MOVIE_LOCAL_LAST_UPDATE, localLastUpdate).commit()
//            }
    }
}