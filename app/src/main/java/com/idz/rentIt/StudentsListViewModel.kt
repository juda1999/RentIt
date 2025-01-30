package com.idz.rentIt

import androidx.lifecycle.ViewModel
import com.idz.rentIt.model.Post

class StudentsListViewModel : ViewModel() {

    private var _posts: List<Post>? = null
    var posts: List<Post>?
        get() = _posts
        private set(value) {
            _posts = value
        }

    fun set(students: List<Post>?) {
        this.posts = students
    }
}