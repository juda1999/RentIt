package com.idz.rentIt.base

import com.idz.rentIt.model.Post

typealias StudentsCallback = (List<Post>) -> Unit
typealias EmptyCallback = () -> Unit

object Constants {

    object COLLECTIONS {
        const val POSTS = "posts"
    }
}