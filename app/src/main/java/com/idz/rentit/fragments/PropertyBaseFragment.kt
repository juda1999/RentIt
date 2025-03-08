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
import androidx.navigation.Navigation.findNavController
import com.idz.rentIt.R
import com.idz.rentit.GuestsActivity
import com.idz.rentit.repository.Repository

abstract class PropertyBaseFragment : Fragment() {

    protected fun configureMenuOptions(view: View) {
        val parentActivity = activity
        parentActivity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.addPropertyFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    findNavController(view).popBackStack()
                    return true
                } else {
                    if (menuItem.itemId == R.id.filterFragment) {
                        findNavController(view).navigate(R.id.filterFragment)
                        return true
                    }
                        if (menuItem.itemId == R.id.logoutMenuItem) {
                            Repository.repositoryInstance.getAuthModel().logout { startIntroActivity() }
                        } else {
                            findNavController(view).navigate(R.id.userProfileFragment)
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
