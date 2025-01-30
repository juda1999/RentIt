package com.idz.rentIt.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentIt.OnItemClickListener
import com.idz.rentIt.R
import com.idz.rentIt.databinding.ActivityStudentsListViewBinding
import com.idz.rentIt.databinding.StudentListRowBinding
import com.idz.rentIt.model.Student
import com.squareup.picasso.Picasso

class StudentViewHolder(
    private val binding: StudentListRowBinding,
    listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(binding.root) {

    private var student: Student? = null

        init {

            binding.checkBox.apply {
                setOnClickListener { view ->
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            binding.nameTextView.text = student?.name
            binding.idTextView.text = student?.id
            binding.checkBox.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }

            student?.avatarUrl?.let { avatarUrl ->
                val url = avatarUrl.ifBlank { return }
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.avatar)
                    .into(binding.imageView)
            }
        }
    }