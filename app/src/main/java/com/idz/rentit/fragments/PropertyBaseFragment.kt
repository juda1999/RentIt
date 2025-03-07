package com.idz.rentit.fragments

import android.content.Intent
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavDirections
import com.idz.rentIt.R
import com.idz.rentit.GuestsActivity
import com.idz.rentit.repository.Repository

abstract class PropertyBaseFragment : Fragment() {

    protected fun configureMenuOptions(view: View) {
        val parentActivity = activity
        parentActivity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack()
                    return true
                } else {
                    if (view != null) {
                        if (menuItem.itemId == R.id.logoutMenuItem) {
                            Repository.repositoryInstance.getAuthModel().logout { startIntroActivity() }
                        } else {
                            val action: NavDirections =
                                PropertyHomeFragmentDirections.actionPropertyListFragmentToUserProfileFragment()
                            Navigation.findNavController(view).navigate(action)
                        }
                        return true
                    }
                }
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
