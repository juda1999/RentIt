package com.idz.rentIt.base

import com.idz.rentIt.model.Student

typealias StudentsCallback = (List<Student>) -> Unit
typealias EmptyCallback = () -> Unit

object Constants {

    object COLLECTIONS {
        const val POSTS = "posts"
    }
}