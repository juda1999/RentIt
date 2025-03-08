package com.idz.rentit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentFilterBinding
import com.idz.rentIt.databinding.FragmentSigninBinding
import com.idz.rentit.listeners.authentication.GetPropertyItemListListener
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property


class FilterFragment : Fragment() {
    private lateinit var viewBindings: FragmentFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentFilterBinding.inflate(inflater, container, false)
        configureMenuOptions(viewBindings.root)
        activateButtonsListeners()
        return viewBindings.root
    }

    private fun activateButtonsListeners() {
        viewBindings.filterFragmentApplyBtn.setOnClickListener {
            val listener = object : GetPropertyItemListListener<Property> {
                    // This is called when the filtering is complete
//                    Repository.repositoryInstance.localModel.propertyHandler.allProperties = properties
//                    Navigation.findNavController(requireView()).popBackStack()

                override fun onComplete(movieItemList: List<Property>?) {
                    TODO("Not yet implemented")
                }
            }
//            val filteredProperties = Repository.repositoryInstance.localModel.propertyHandler.getAllPropertiesByFilter(
//            viewBindings.filterPriceInput.value.toDouble(),
//            viewBindings.filterFragmentLocationInputEt.text.toString(),
//            viewBindings.filterFragmentShelterInput.isChecked,
//            viewBindings.filterFragmentFurnishedInput.isChecked,
//           listener)
        }
    }

    private fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = requireActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.logoutMenuItem)
                menu.removeItem(R.id.filterFragment)
                menu.removeItem(R.id.addPropertyFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack()
                    true
                } else {
                    false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}