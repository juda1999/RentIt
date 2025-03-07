package com.idz.rentit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentAddPropertyBinding
import com.idz.rentit.GuestsActivity
import com.idz.rentit.repository.Repository

class AddPropertyFragment : Fragment() {
    private lateinit var viewBindings: FragmentAddPropertyBinding
    private lateinit var numberPicker: NumberPicker
//    private lateinit var viewModel: AddPropertyFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentAddPropertyBinding.inflate(inflater, container, false)
        numberPicker = viewBindings.addPropertyNumberPicker
        configureNumberPicker()
        activateButtonsListeners()
        configureMenuOptions(viewBindings.root)
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

    private fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = activity as FragmentActivity
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.propertyHomeFragment)
                menu.removeItem(R.id.addPropertyFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.logoutMenuItem) {
                    Repository.repositoryInstance.getAuthModel()
                        .logout {
                            startIntroActivity()
                        }
                    return true
                } else {
                    if (menuItem.itemId == android.R.id.home) {
                        findNavController().popBackStack()
                        return true
                    }                }
                return false
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED)
    }

    protected fun startIntroActivity() {
        activity?.let {
            val introActivityIntent = Intent(it, GuestsActivity::class.java)
            startActivity(introActivityIntent)
            it.finish()
        }
    }
}