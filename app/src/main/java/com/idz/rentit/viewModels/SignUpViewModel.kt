package com.idz.rentit.viewModels

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SignUpFragmentViewModel : ViewModel() {
    private var navController: NavController? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var isProfilePictureSelected: Boolean = false

    fun setNavController(navController: NavController?) {
        this.navController = navController
    }

    fun getCameraLauncher(): ActivityResultLauncher<Void?>? {
        return cameraLauncher
    }

    fun setCameraLauncher(cameraLauncher: ActivityResultLauncher<Void?>?) {
        this.cameraLauncher = cameraLauncher
    }

    fun isProfilePictureSelected(): Boolean {
        return isProfilePictureSelected
    }

    fun setProfilePictureSelected(profilePictureSelected: Boolean) {
        isProfilePictureSelected = profilePictureSelected
    }
}
