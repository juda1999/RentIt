package com.idz.rentit.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.repository.firebase.AuthModel
import com.idz.rentit.repository.firebase.FirebaseModel
import com.idz.rentit.repository.models.*
import com.idz.rentit.repository.room.LocalModel
import java.util.Objects
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Repository private constructor() {
    val localModel: LocalModel = LocalModel()
    private val firebaseModel: FirebaseModel = FirebaseModel()
    private val authModel: AuthModel = AuthModel()
    val executor: Executor = Executors.newSingleThreadExecutor()
    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var properties: LiveData<List<Property>>? = null

    fun getFirebaseModel(): FirebaseModel {
        return this.firebaseModel
    }

    fun getAuthModel(): AuthModel {
        return this.authModel
    }

    val allProperties: LiveData<List<Property>>?
        get() {
            if (Objects.isNull(this.properties)) {
                this.properties = localModel.propertyHandler.allProperties
                refreshAllProperties()
                Log.d("REPO", "Properties received: ${this.properties!!.value}")
            }
            return this.properties
        }


//    val allMovieComments: LiveData<List<Any>>?
//        get() {
//            if (Objects.isNull(this.movieComments)) {
//                this.movieComments =
//                    localModel.getMovieCommentHandler().getAllMovieComments()
//                refreshAllMovieComments()
//            }
//            return this.movieComments
//        }

//    fun refreshAllMovieCategories() {
//        NotificationManager.instance().getEventMovieCategoryListLoadingState().setValue(LOADING)
//        val localLastUpdate: Long = MovieCategory.getLocalLastUpdate()
//        getFirebaseModel().getMovieCategoryExecutor()
//            .getAllMovieCategoriesSinceLastUpdate(localLastUpdate) { movieCategories ->
//                executor.execute {
//                    Log.d(
//                        "TAG",
//                        "MovieCategory: firebase return : " + movieCategories.size()
//                    )
//                    var movieCategoryGlobalLastUpdate = localLastUpdate
//                    for (movieCategory in movieCategories) {
//                        localModel.getMovieCategoryHandler()
//                            .addMovieCategory(movieCategory)
//                        if (movieCategoryGlobalLastUpdate < movieCategory.getCategoryLastUpdate()) {
//                            movieCategoryGlobalLastUpdate = movieCategory.getCategoryLastUpdate()
//                        }
//                    }
//                    MovieCategory.setLocalLastUpdate(movieCategoryGlobalLastUpdate)
//                    NotificationManager.instance()
//                        .getEventMovieCategoryListLoadingState().postValue(NOT_LOADING)
//                }
//            }
//    }

     fun refreshAllProperties() { /// do we need this??? and do we need to add local last update
        NotificationManager.instance().getEventPropertyListLoadingState().value = LoadingState.LOADING
         val localLastUpdate: Long = Property.getLocalLastUpdate()
        getFirebaseModel().propertyExecutor.getAllPropertiesSinceLastUpdate(localLastUpdate) { properties ->
            executor.execute {
                    Log.d("MOR", "Properties: firebase return : $properties")
                var propertyGlobalLastUpdate = localLastUpdate
                for (property in properties) {
                    localModel.propertyHandler.addProperty(property)
                    if (propertyGlobalLastUpdate < property.lastUpdate!!) {
                        propertyGlobalLastUpdate = property.lastUpdate!!
                    }
                    Log.d("MORRRRR","${localModel.propertyHandler.allProperties.value}")
                }
                Property.setLocalLastUpdate(propertyGlobalLastUpdate)
                NotificationManager.instance()
                    .getEventPropertyListLoadingState().postValue(LoadingState.NOT_LOADING)
            }
        }
    }

//    fun refreshAllMovieComments() {
//        NotificationManager.instance().getEventMovieCommentListLoadingState().setValue(LOADING)
//        val localLastUpdate: Long = MovieComment.getLocalLastUpdate()
//        getFirebaseModel().getMovieCommentExecutor()
//            .getAllMovieCommentsSinceLastUpdate(localLastUpdate) { movieComments ->
//                executor.execute {
//                    Log.d("TAG", "MovieComment: firebase return : " + movieComments.size())
//                    var movieCommentGlobalLastUpdate = localLastUpdate
//                    for (movieComment in movieComments) {
//                        localModel.getMovieCommentHandler()
//                            .addMovieComment(movieComment)
//                        if (movieCommentGlobalLastUpdate < movieComment.getMovieCommentLastUpdate()) {
//                            movieCommentGlobalLastUpdate = movieComment.getMovieCommentLastUpdate()
//                        }
//                    }
//                    MovieComment.setLocalLastUpdate(movieCommentGlobalLastUpdate)
//                    NotificationManager.instance()
//                        .getEventMovieCommentListLoadingState().postValue(NOT_LOADING)
//                }
//            }
//    }

    fun register(addUserListener: () -> Unit, user: User, password: String) {
        authModel.register(user.email, password) { uid: String ->
            user.userId = uid
            firebaseModel.userExecutor.addUser(user, addUserListener)
        }
    }

    companion object {
        val repositoryInstance: Repository = Repository()
    }
}