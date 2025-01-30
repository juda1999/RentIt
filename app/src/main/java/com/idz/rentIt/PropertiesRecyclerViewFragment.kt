package com.idz.rentIt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idz.rentIt.model.Model
import com.idz.rentIt.model.Post

class PropertiesRecyclerViewFragment : Fragment() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemClick(student: Post?)
    }
    private var students: MutableList<Post>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_properties_recycler_view, container, false)
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
        return view
    }

}