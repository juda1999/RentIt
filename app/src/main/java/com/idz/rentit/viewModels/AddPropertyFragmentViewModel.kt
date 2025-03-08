package com.idz.rentit.viewModels

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel

class AddPropertyFragmentViewModel : ViewModel() {
    var cameraLauncher: ActivityResultLauncher<Void?>? = null
    var isPropertyPictureSelected: Boolean = false
}