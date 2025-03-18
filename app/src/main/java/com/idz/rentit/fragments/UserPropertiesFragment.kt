package com.idz.rentit.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentPropertyHomeBinding
import com.idz.rentIt.databinding.FragmentPropertyProfileBinding
import com.idz.rentIt.databinding.FragmentUserPropertiesBinding
import com.idz.rentit.GuestsActivity
import com.idz.rentit.adapters.PropertyAdapter
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import com.idz.rentit.viewModels.PropertyHomeFragmentViewModel
import com.idz.rentit.viewModels.UserPropertiesFragmentViewModel
import kotlin.math.log

class UserPropertiesFragment : PropertyBaseFragment() {
    private lateinit var viewBindings: FragmentUserPropertiesBinding
    private lateinit var viewModel: UserPropertiesFragmentViewModel
    private lateinit var propertyAdapter: PropertyAdapter
    private var userId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentUserPropertiesBinding.inflate(inflater, container, false)
        viewBindings.swipeRefresh.setOnRefreshListener { this.reloadPropertyList() }
        configureMenuOptions(viewBindings.root)
        userId = Repository.repositoryInstance.getAuthModel().currentUserUid
        return viewBindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBindings.propertyListRecyclerView.setHasFixedSize(true)
        viewBindings.propertyListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        propertyAdapter = PropertyAdapter(layoutInflater, mutableListOf<Property>())
        viewBindings.propertyListRecyclerView.adapter = propertyAdapter
        viewModel.getPropertyList(userId!!).observe(viewLifecycleOwner) { properties ->
            Log.d("UserPropertiesFragment", "Users properties received: ${properties.size}")
            propertyAdapter.updatePropertyList(properties)
        }
        activateItemListListener()
        NotificationManager.instance()
            .getEventPropertyListLoadingState()
            .observe(viewLifecycleOwner) { loadingState ->
                viewBindings.swipeRefresh.isRefreshing = loadingState == LoadingState.LOADING
            }
    }

    private fun reloadPropertyList() {
        Repository.repositoryInstance.refreshAllProperties()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[UserPropertiesFragmentViewModel::class.java]
    }

    private fun activateItemListListener() {
        this.propertyAdapter.setOnItemClickListener { position, property ->
            val action = UserPropertiesFragmentDirections
                .actionUserPropertiesFragmentToPropertyProfileFragment(property!!)
            findNavController().navigate(action)
        }
    }

    override fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = requireActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.filterFragment)
                menu.removeItem(R.id.userPropertyFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                 if (menuItem.itemId == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack()
                    return true
                }
                if(menuItem.itemId == R.id.logoutMenuItem) {
                    Repository.repositoryInstance.getAuthModel()
                        .logout {
                            startIntroActivity()
                        }
                    return true
                }

                if(menuItem.itemId == R.id.addPropertyFragment) {
                    val action: NavDirections =
                        UserPropertiesFragmentDirections.actionUserPropertiesFragmentToAddPropertyFragment(null)
                    Navigation.findNavController(view).navigate(action);
                    return true
                }
                else {
                    return false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}