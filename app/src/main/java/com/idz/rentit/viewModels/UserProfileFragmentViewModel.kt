package com.idz.rentit.viewModels

import androidx.lifecycle.ViewModel
import com.idz.rentit.repository.models.User

class UserProfileFragmentViewModel : ViewModel() {
    private var user: User? = null

    fun getUser(): User? {
        return user
    }

    fun setUser(user: User) {
        this.user = user
    }
}
