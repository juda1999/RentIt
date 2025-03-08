package com.idz.rentit.viewModels

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.models.User

class EditUserProfileFragmentViewModel : ViewModel() {
    var user: User? = null
    var cameraLauncher: ActivityResultLauncher<Void?>? = null
    var isProfilePictureSelected: Boolean = false
}