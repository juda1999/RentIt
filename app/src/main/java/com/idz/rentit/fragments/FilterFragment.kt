package com.idz.rentit.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentFilterBinding
import com.idz.rentit.repository.Repository
import com.idz.rentit.viewModels.CitiesViewModel


class FilterFragment : Fragment() {
    private lateinit var numberPicker: NumberPicker
    private lateinit var viewBindings: FragmentFilterBinding
    private lateinit var citiesViewModel: CitiesViewModel
    private lateinit var cities: List<String>;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentFilterBinding.inflate(inflater, container, false)
        configureMenuOptions(viewBindings.root)
        numberPicker = viewBindings.filterPriceInput
        citiesViewModel.fetchCities()
        citiesViewModel.cities.observe(viewLifecycleOwner, Observer { cityList ->
            this.cities = cityList
            populateCitySpinner();
        })
        configureNumberPicker()
        activateButtonsListeners()
        return viewBindings.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.citiesViewModel = ViewModelProvider(this)[CitiesViewModel::class.java]
    }

    private fun populateCitySpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, this.cities)
        viewBindings.locationAutocompletTextView.setAdapter(adapter)
    }

    private fun configureNumberPicker() {
        numberPicker.minValue = 100
        numberPicker.maxValue = 10000
        numberPicker.value = 100
        numberPicker.wrapSelectorWheel = false
    }

    private fun activateButtonsListeners() {
        viewBindings.filterFragmentClearBtn.setOnClickListener {
            Repository.repositoryInstance.applyFilter(null, null, null, null)
            Repository.repositoryInstance.setFilteredProperties()
            Repository.repositoryInstance.allFilteredProperties.observe(viewLifecycleOwner, Observer {
                findNavController().navigateUp()
            })
        }
        viewBindings.filterFragmentApplyBtn.setOnClickListener {
            val price = viewBindings.filterPriceInput.value.toDouble()
            val location = viewBindings.locationAutocompletTextView.text.toString()
            val shelter = viewBindings.filterFragmentShelterInput.isChecked
            val furnished = viewBindings.filterFragmentFurnishedInput.isChecked
            Repository.repositoryInstance.applyFilter(price, location, shelter, furnished)
            Repository.repositoryInstance.setFilteredProperties()
            Repository.repositoryInstance.allFilteredProperties.observe(viewLifecycleOwner, Observer {
                findNavController().navigateUp()
            })
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