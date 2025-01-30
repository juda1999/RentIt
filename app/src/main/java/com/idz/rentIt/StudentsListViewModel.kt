package com.idz.rentIt

import androidx.lifecycle.ViewModel
import com.idz.rentIt.model.Student

class StudentsListViewModel : ViewModel() {

    private var _students: List<Student>? = null
    var students: List<Student>?
        get() = _students
        private set(value) {
            _students = value
        }

    fun set(students: List<Student>?) {
        this.students = students
    }
}