package com.idz.rentit.repository.room

import com.example.movieshare.repository.room.handlers.PropertyHandler

class LocalModel {
//    val movieCategoryHandler: MovieCategoryHandler
//        get() = MovieCategoryHandler.instance()
//
    val propertyHandler: PropertyHandler
        get() = PropertyHandler.instance()

//    val movieCommentHandler: MovieCommentHandler
//        get() = MovieCommentHandler.instance()
}