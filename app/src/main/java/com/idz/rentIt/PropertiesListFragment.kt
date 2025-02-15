package com.idz.rentIt

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.idz.rentIt.adapter.StudentsRecyclerAdapter
import com.idz.rentIt.databinding.FragmentStudentsListBinding
import com.idz.rentIt.model.Model
import com.idz.rentIt.model.Post

class PropertiesListFragment : Fragment() {
    private var adapter: StudentsRecyclerAdapter? = null
    private var binding: FragmentStudentsListBinding? = null
    private var viewModel: PropertiesListViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PropertiesListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentsListBinding.inflate(inflater, container, false)

        // TODO: Set DB ✅
        // TODO: Refactor Model to support local db ✅
        // TODO: Refactor Fragments to work with live data ✅
        // TODO: Add progress indicator for better UX
        // TODO: Migrate to ViewBinding


        binding?.recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        adapter = StudentsRecyclerAdapter(viewModel?.posts)

        adapter?.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("TAG", "On click Activity listener on position $position")
            }

            override fun onItemClick(post: Post?) {
                Log.d("TAG", "On student clicked name: ${post?.address}")

//                Navigation.findNavController(view).navigate(R.id.action_studentsListFragment_to_blueFragment)

                post?.let {
                    val action = PropertiesListFragmentDirections.actionStudentsListFragmentToBlueFragment(it.address)
                    binding?.root?.let {
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }
        }

        binding?.recyclerView?.adapter = adapter

        val action = PropertiesListFragmentDirections.actionGlobalAddStudentFragment()
        binding?.addStudentButton?.setOnClickListener(Navigation.createNavigateOnClickListener(action))

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        getAllStudents()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun getAllStudents() {
        binding?.progressBar?.visibility = View.VISIBLE

        Model.shared.getAllStudents {
            viewModel?.set(students = it)
            adapter?.set(it)
            adapter?.notifyDataSetChanged()

            binding?.progressBar?.visibility = View.GONE
        }
    }
}