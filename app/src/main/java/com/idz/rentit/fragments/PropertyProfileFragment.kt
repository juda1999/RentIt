package com.idz.rentit.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.movieshare.utils.RoundedTransformation
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentPropertyProfileBinding
import com.idz.rentit.GuestsActivity
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import com.idz.rentit.viewModels.PropertyProfileFragmentViewModel
import com.squareup.picasso.Picasso

class PropertyProfileFragment: Fragment() {
    private var property: Property? = null
    private lateinit var viewBindings: FragmentPropertyProfileBinding
    private lateinit var viewModel: PropertyProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            property = PropertyProfileFragmentArgs.fromBundle(it).property
        }
        viewModel.setProperty(property!!)
        viewBindings = FragmentPropertyProfileBinding.inflate(inflater, container, false)
        this.configureMenuOptions(viewBindings.root)
        activateButtonsListeners()
        displayPropertyDetails()
        return viewBindings.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PropertyProfileFragmentViewModel::class.java]
    }

    private fun displayPropertyDetails() {
        var userId = Repository.repositoryInstance.getAuthModel().currentUserUid

        viewModel.getProperty()?.let { property ->
            viewBindings.propertyProfileFragmentLocationValue.setText(property.location)
            viewBindings.propertyProfileFragmentDescriptionValue.setText(property.description)
            viewBindings.propertyProfileFragmentPriceInput.setText(property.price.toString())
            viewBindings.propertyProfileFragmentFurnishedInput.setText(if (property.isFurnished) "Yes" else "No")
            viewBindings.propertyProfileFragmentShelterInput.setText(if (property.hasShelter) "Yes" else "No")
            Repository.repositoryInstance.getFirebaseModel().userExecutor.getUserById(property.userId) { user ->
                viewBindings.propertyProfileFragmentUserValue.setText(user.firstName)
            }
            if(property.userId == userId) {
                viewBindings.propertyProfileFragmentEditBtn.visibility = View.VISIBLE
            }

            loadImage(
                property.imageUrl,
                R.drawable.home,
                viewBindings.propertyProfileFragmentImg
            )
        }
    }

    private fun loadImage(url: String?, placeholder: Int, item: ImageView) {
        url?.let {
            Picasso.get().load(it).transform(RoundedTransformation(30, 0))
                .placeholder(placeholder)
                .into(item)
        } ?: run {
            item.setImageResource(placeholder)
        }
    }

    private fun activateButtonsListeners() {
        viewBindings.propertyProfileFragmentEditBtn.setOnClickListener { view ->
            val action: NavDirections = PropertyProfileFragmentDirections.
                actionPropertyProfileFragmentToAddPropertyFragment(viewModel.getProperty()!!)
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = requireActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.addPropertyFragment)
                menu.removeItem(R.id.filterFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.propertyHomeFragment) {
                    Navigation.findNavController(view).popBackStack()
                    return true
                } else {
                    if (menuItem.itemId == R.id.logoutMenuItem) {
                        Repository.repositoryInstance.getAuthModel()
                            .logout {
                                startIntroActivity()
                            }
                        return true
                    }
                    if (menuItem.itemId == android.R.id.home) {
                        findNavController().popBackStack()
                        return true
                    }                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    protected fun startIntroActivity() {
        activity?.let {
            val introActivityIntent = Intent(it, GuestsActivity::class.java)
            startActivity(introActivityIntent)
            it.finish()
        }
    }
}