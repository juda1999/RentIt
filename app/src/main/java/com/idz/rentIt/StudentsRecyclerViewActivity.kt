package com.idz.rentIt

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idz.rentIt.model.Post

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemClick(student: Post?)
}

class StudentsRecyclerViewActivity : AppCompatActivity() {

    private var students: MutableList<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO: 1. Create layout ✅
        // TODO: 2. Create adapter ✅
        // TODO: 3. Create ViewHolder ✅
//
//        students = Model.shared.students
//        val recyclerView: RecyclerView = findViewById(R.id.students_list_activity_recycler_view)
//        recyclerView.setHasFixedSize(true)
//
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//
//        val adapter = StudentsRecyclerAdapter(students)
//
//        adapter.listener = object : OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                Log.d("TAG", "On click Activity listener on position $position")
//            }
//
//            override fun onItemClick(student: Student?) {
//                Log.d("TAG", "On student clicked name: ${student?.name}")
//            }
//        }
//
//        recyclerView.adapter = adapter

    }
}