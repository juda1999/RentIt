package com.idz.rentit.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.idz.rentIt.databinding.FragmentPropertyHomeBinding
import com.idz.rentIt.fragments.PropertyBaseFragment
import com.idz.rentit.adapters.PropertyAdapter
import com.idz.rentit.enums.LoadingState
import com.idz.rentit.notifications.NotificationManager
import com.idz.rentit.viewmodels.PropertyHomeFragmentViewModel

class PropertyHomeFragment : PropertyBaseFragment() {
    private lateinit var viewBindings: FragmentPropertyHomeBinding
    private lateinit var viewModel: PropertyHomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val displayMetrics: DisplayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        viewBindings = FragmentPropertyHomeBinding.inflate(inflater, container, false)
        viewBindings.propertyListFragmentPropertyList.setHasFixedSize(true)
        viewBindings.propertyListFragmentPropertyList.layoutManager = GridLayoutManager(requireContext(), (dpWidth / 137).toInt())
        viewBindings.propertyListFragmentPropertyList.adapter = PropertyAdapter(layoutInflater, viewModel.getPropertyList().value!!)
        configureMenuOptions(viewBindings.root)
        NotificationManager.instance()
            .getEventPropertyListLoadingState()
            .observe(viewLifecycleOwner) { loadingState ->
                viewBindings.swipeRefresh.isRefreshing = loadingState == LoadingState.LOADING
            }
        return viewBindings.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(PropertyHomeFragmentViewModel::class.java)
    }
}
