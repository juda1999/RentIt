package com.idz.colman24class2

import androidx.lifecycle.ViewModel
import com.idz.colman24class2.model.Student

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