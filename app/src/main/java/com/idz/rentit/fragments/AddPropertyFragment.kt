package com.idz.rentit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentAddPropertyBinding

class AddPropertyFragment : Fragment() {
    private lateinit var viewBindings: FragmentAddPropertyBinding
    private lateinit var numberPicker: NumberPicker
//    private lateinit var viewModel: AddPropertyFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBindings = FragmentAddPropertyBinding.inflate(inflater, container, false)
        numberPicker = viewBindings.addPropertyNumberPicker
        configureNumberPicker()
        activateButtonsListeners()
        return viewBindings.root
    }

    private fun configureNumberPicker() {
        numberPicker.minValue = 100
        numberPicker.maxValue = 10000
        numberPicker.value = 100
        numberPicker.wrapSelectorWheel = false
    }

    private fun activateButtonsListeners() {
        viewBindings.addPropertyFragmentUploadBtn.setOnClickListener {
            val selectedValue = numberPicker.value
//            Toast.makeText(context, "Selected value: $selectedValue", Toast.LENGTH_SHORT).show()
            // ... do something with the selectedValue ...
        }
    }
}