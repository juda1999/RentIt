package com.idz.rentit.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.idz.rentIt.databinding.FragmentPropertyHomeBinding
import com.idz.rentit.adapters.PropertyAdapter
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import com.idz.rentit.viewModels.PropertyHomeFragmentViewModel


class PropertyHomeFragment : PropertyBaseFragment() {
    private lateinit var viewBindings: FragmentPropertyHomeBinding
    private lateinit var viewModel: PropertyHomeFragmentViewModel
    private lateinit var propertyAdapter: PropertyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindings = FragmentPropertyHomeBinding.inflate(inflater, container, false)
        viewBindings.swipeRefresh.setOnRefreshListener { this.reloadPropertyList() }
        configureMenuOptions(viewBindings.root)
        return viewBindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBindings.propertyListRecyclerView.setHasFixedSize(true)
        viewBindings.propertyListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        // Initialize the adapter with an empty mutable list
        propertyAdapter = PropertyAdapter(layoutInflater, mutableListOf<Property>())
        viewBindings.propertyListRecyclerView.adapter = propertyAdapter
        viewModel.getPropertyList().observe(viewLifecycleOwner) { properties ->
            // Update the adapter with the new data
            Log.d("PropertyHomeFragment", "Properties received: ${properties.size}")
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
        viewModel = ViewModelProvider(this)[PropertyHomeFragmentViewModel::class.java]
    }

    private fun activateItemListListener() {
        this.propertyAdapter.setOnItemClickListener { position ->
            val action = PropertyHomeFragmentDirections
                .actionPropertyListFragmentToUserProfileFragment(position!!)
            findNavController().navigate(action)
        }
    }
}