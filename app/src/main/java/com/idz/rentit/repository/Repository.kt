package com.idz.rentit.repository

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.idz.rentit.enums.LoadingState.*
import com.idz.rentit.listeners.authentication.AddUserListener
import com.idz.rentit.repository.firebase.AuthModel
import com.idz.rentit.repository.firebase.FirebaseModel
import com.idz.rentit.repository.models.*
import com.idz.rentit.repository.room.LocalModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Repository private constructor() {
    private val localModel: LocalModel = LocalModel()
    private val firebaseModel: FirebaseModel = FirebaseModel()
    private val authModel: AuthModel = AuthModel()
    val executor: Executor = Executors.newSingleThreadExecutor()
    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
//    private var movieCategories: LiveData<List<MovieCategory>>? = null
//    private var movies: LiveData<List<Movie>>? = null
//    private var movieComments: LiveData<List<MovieComment>>? = null

    fun getLocalModel(): LocalModel {
        return this.localModel
    }

    fun getFirebaseModel(): FirebaseModel {
        return this.firebaseModel
    }

    fun getAuthModel(): AuthModel {
        return this.authModel
    }

//    val allMovieCategories: LiveData<List<Any>>?
//        get() {
//            if (Objects.isNull(this.movieCategories)) {
//                this.movieCategories =
//                    localModel.getMovieCategoryHandler().getAllMovieCategories()
//                refreshAllMovieCategories()
//            }
//            return this.movieCategories
//        }
//
//    val allMovies: LiveData<List<Movie>>?
//        get() {
//            if (Objects.isNull(this.movies)) {
//                this.movies = localModel.getMovieHandler().getAllMovies()
//                refreshAllMovies()
//            }
//            return this.movies
//        }

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

//    fun refreshAllMovies() {
//        NotificationManager.instance().getEventMovieListLoadingState().setValue(LOADING)
//        val localLastUpdate: Long = Movie.getLocalLastUpdate()
//        getFirebaseModel().getMovieExecutor()
//            .getAllMoviesSinceLastUpdate(localLastUpdate) { movies ->
//                executor.execute {
//                    Log.d("TAG", "Movie: firebase return : " + movies.size())
//                    var movieGlobalLastUpdate = localLastUpdate
//                    for (movie in movies) {
//                        localModel.getMovieHandler().addMovie(movie)
//                        if (movieGlobalLastUpdate < movie.getMovieLastUpdate()) {
//                            movieGlobalLastUpdate = movie.getMovieLastUpdate()
//                        }
//                    }
//                    Movie.setLocalLastUpdate(movieGlobalLastUpdate)
//                    NotificationManager.instance()
//                        .getEventMovieListLoadingState().postValue(NOT_LOADING)
//                }
//            }
//    }

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

    fun register(addUserListener: AddUserListener?, user: User, password: String?) {
        authModel.register(user.email, password) { uid ->
            user.setUserId(uid)
            firebaseModel.userExecutor.addUser(user, addUserListener)
        }
    }

    companion object {
        val repositoryInstance: Repository = Repository()
    }
}