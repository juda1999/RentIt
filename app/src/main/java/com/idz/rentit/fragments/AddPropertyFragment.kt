package com.idz.rentit.fragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentAddPropertyBinding
import com.idz.rentit.GuestsActivity
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import com.idz.rentit.viewModels.AddPropertyFragmentViewModel
import com.idz.rentit.viewModels.CitiesViewModel
import java.util.Objects
import java.util.UUID

class AddPropertyFragment : Fragment() {
    private lateinit var viewBindings: FragmentAddPropertyBinding
    private lateinit var citiesViewModel: CitiesViewModel
    private lateinit var numberPicker: NumberPicker
    private lateinit var viewModel: AddPropertyFragmentViewModel
    private lateinit var cities: List<String>;
    private var userId: String? = null
    private var editingProperty: Property? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentAddPropertyBinding.inflate(inflater, container, false)
        numberPicker = viewBindings.addPropertyFragmentPriceInput
        configureNumberPicker()
        arguments?.let {
            editingProperty = AddPropertyFragmentArgs.fromBundle(it).property
        }
        citiesViewModel.fetchCities()
        citiesViewModel.cities.observe(viewLifecycleOwner, Observer { cityList ->
            this.cities = cityList
            populateCitySpinner();
        })
        if(editingProperty != null) {
            displayEditedProperty()
        }
        this.userId = Repository.repositoryInstance.getAuthModel().currentUserUid;
        activateButtonsListeners()
        configureMenuOptions(viewBindings.root)
        viewModel.cameraLauncher = (registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { result ->
            if (Objects.nonNull(result)) {
                viewBindings.addPropertyFragmentImg.setImageBitmap(result)
                viewModel.isPropertyPictureSelected = (true)
            }
        })
        return viewBindings.root
    }

    private fun displayEditedProperty() {
        viewBindings.addPropertyFragmentDeleteBtn.visibility = View.VISIBLE
        viewBindings.addPropertyFragmentDeleteBtn.setOnClickListener {
            Repository.repositoryInstance.getFirebaseModel().propertyExecutor.deleteProperty(this.editingProperty!!.propertyId) {
                this.navigateToHomePageAfterAddProperty()
                Repository.repositoryInstance.localModel.propertyHandler.deleteProperty(this.editingProperty!!.propertyId)

            }
        }
        viewBindings.signUpFragmentHeadline.setText("Edit property")
        viewBindings.addPropertyFragmentPriceInput.value = editingProperty?.price?.toInt() ?: 100;
        viewBindings.addPropertyFragmentDescriptionInputEt.setText(editingProperty?.description)
        viewBindings.addPropertyFragmentShelterInput.isChecked = editingProperty?.hasShelter ?: false
        viewBindings.addPropertyFragmentFurnishedInput.isChecked = editingProperty?.isFurnished ?: false
        if (editingProperty?.imageUrl != null) {
            //add photo
        }


    }
    private fun populateCitySpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, this.cities)
        viewBindings.addPropertyFragmentLocationInputAutocomplete.setAdapter(adapter)

        if(editingProperty != null) {
                viewBindings.addPropertyFragmentLocationInputAutocomplete.setText(editingProperty!!.location)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.viewModel = ViewModelProvider(this)[AddPropertyFragmentViewModel::class.java]
        this.citiesViewModel = ViewModelProvider(this)[CitiesViewModel::class.java]
    }

    private fun configureNumberPicker() {
        numberPicker.minValue = 100
        numberPicker.maxValue = 10000
        numberPicker.value = 100
        numberPicker.wrapSelectorWheel = false
    }

    private fun activateButtonsListeners() {
        viewBindings.addPropertyFragmentUploadBtn.setOnClickListener {
            val propertyId = editingProperty?.propertyId ?: UUID.randomUUID().toString()
            Repository.repositoryInstance.getFirebaseModel().propertyExecutor.uploadPropertyImage(
                (viewBindings.addPropertyFragmentImg.drawable as BitmapDrawable).bitmap,
                propertyId,
                requireContext()
            ) { propertyImage ->
//            var propertyImage: String = null.toString()
//            if (viewModel.isPropertyPictureSelected) {
//                propertyImage = (viewBindings.addPropertyFragmentImg.drawable as BitmapDrawable).bitmap.toString()
//            }

                val property = Property(
                    propertyId = propertyId,
                    userId = userId.toString(),
                    location = viewBindings.addPropertyFragmentLocationInputAutocomplete.text.toString(),
                    description = viewBindings.addPropertyFragmentDescriptionInputEt.text.toString(),
                    price = numberPicker.value.toLong(),
                    imageUrl = propertyImage ?: "",
                    hasShelter = viewBindings.addPropertyFragmentShelterInput.isChecked,
                    isFurnished = viewBindings.addPropertyFragmentFurnishedInput.isChecked,
                )
                Repository.repositoryInstance.getFirebaseModel().propertyExecutor.addProperty(
                    property
                ) { this.navigateToHomePageAfterAddProperty() }

                viewBindings.cameraButton.setOnClickListener {
                    viewModel.cameraLauncher?.launch(null)
                }
            }
        }
    }

    private fun navigateToHomePageAfterAddProperty() {
        Repository.repositoryInstance.refreshAllProperties()
        findNavController().navigate(R.id.action_addPropertyFragment_to_propertyHomeFragment)
    }

    private fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = activity as FragmentActivity
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.propertyHomeFragment)
                menu.removeItem(R.id.addPropertyFragment)
                menu.removeItem(R.id.filterFragment)
                menu.removeItem(R.id.userPropertyFragment)
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