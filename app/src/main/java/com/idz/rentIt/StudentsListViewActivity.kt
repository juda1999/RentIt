package com.idz.rentIt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idz.rentIt.model.Post

class StudentsListViewActivity : AppCompatActivity() {
    var posts: MutableList<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO: 1. Set xml layout ✅
        // TODO: 2. Set instance of list view in activity ✅
        // TODO: 3. Set adapter ✅
        // TODO: 4. Create rows layout ✅
        // TODO: 5. Set dynamic data (MVP) 👨‍🎓
        // TODO: 6. On click on checkbox

//        students = Model.shared.students
//        val listView: ListView = findViewById(R.id.students_list_view)
//        listView.adapter = StudentsAdapter()
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
                R.layout.student_list_row,
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