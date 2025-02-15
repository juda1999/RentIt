package com.idz.rentIt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.idz.rentIt.model.Post


class PropertiesListViewFragment : Fragment() {
    var posts: MutableList<Post>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        students = Model.shared.students
//        val listView: ListView = findViewById(R.id.students_list_view)
//        listView.adapter = StudentsAdapter()
        return inflater.inflate(R.layout.fragment_properties_list_view, container, false)
    }

    inner class StudentsAdapter(): BaseAdapter() {
        override fun getCount(): Int = posts?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflation = LayoutInflater.from(parent?.context)
            val view = convertView ?: inflation.inflate(
                R.layout.property_list_row,
                parent,
                false
            ).apply {
                findViewById<CheckBox>(R.id.checkBox).apply {
                    setOnClickListener { view ->
                        (tag as? Int)?.let { tag ->
                            val student = posts?.get(tag)
                            student?.isFurnished = (view as? CheckBox)?.isChecked ?: false
                        }
                    }
                }
            }

//            var view = convertView
//            if (view == null) {
//                view = inflation.inflate(R.layout.student_list_row, parent, false)
//                Log.d("TAG", "Inflating position $position")
//                val checkBox: CheckBox? = view?.findViewById(R.id.student_row_check_box)
////                checkBox?.setOnClickListener {
////                    student?.isChecked = checkBox.isChecked
////                }
//
//                checkBox?.apply {
//                    setOnClickListener { view ->
//                        (tag as? Int)?.let { tag ->
//                            val student = students?.get(tag)
//                            student?.isChecked = (view as? CheckBox)?.isChecked ?: false
//                        }
//                    }
//                }
//            }

            val student = posts?.get(position)

            val nameTextView: TextView? = view?.findViewById(R.id.nameTextView)
            val idTextView: TextView? = view?.findViewById(R.id.idTextView)
            val checkBox: CheckBox? = view?.findViewById(R.id.checkBox)

            nameTextView?.text = student?.address
            idTextView?.text = student?.id

            checkBox?.apply {
                isChecked = student?.isFurnished ?: false
                tag = position
            }
//            checkBox.setOnClickListener {
//                student?.isChecked = checkBox.isChecked
//            }
            return view!!
        }
    }
}