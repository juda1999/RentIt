package com.idz.colman24class2.base

import com.idz.colman24class2.model.Student

typealias StudentsCallback = (List<Student>) -> Unit
typealias EmptyCallback = () -> Unit

object Constants {

    object COLLECTIONS {
        const val STUDENTS = "students"
    }
}