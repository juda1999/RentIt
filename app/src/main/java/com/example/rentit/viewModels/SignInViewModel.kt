package com.example.rentit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SignInFragmentViewModel : ViewModel() {
    private var navController: NavController? = null

    fun getNavController(): NavController? {
        return navController
    }

    fun setNavController(navController: NavController?) {
        this.navController = navController
    }
}
