package com.idz.rentIt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentIt.OnItemClickListener
import com.idz.rentIt.databinding.StudentListRowBinding
import com.idz.rentIt.model.Post

class StudentsRecyclerAdapter(private var students: List<Post>?): RecyclerView.Adapter<StudentViewHolder>() {

        var listener: OnItemClickListener? = null

        fun set(students: List<Post>?) {
            this.students = students
        }

        override fun getItemCount(): Int = students?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val inflator = LayoutInflater.from(parent.context)
            val binding = StudentListRowBinding.inflate(inflator, parent, false)
            return StudentViewHolder(binding, listener)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }
    }