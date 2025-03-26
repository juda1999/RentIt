package com.idz.rentit.utils

import com.idz.rentit.constants.AuthConstants.REGISTER_INVALID_NAME
import com.google.android.material.textfield.TextInputEditText
import com.idz.rentit.constants.AuthConstants.REGISTER_INVALID_EMAIL
import com.idz.rentit.constants.AuthConstants.REGISTER_INVALID_PASSWORD

object UserUtils {

    fun setErrorIfFirstNameIsInvalid(name: TextInputEditText) {
        if (!InputValidator.isFirstNameValid(name.text)) {
            name.error = REGISTER_INVALID_NAME
        } else {
            name.error = null
        }
    }

    fun setErrorIfLastNameIsInvalid(name: TextInputEditText) {
        if (!InputValidator.isLastNameValid(name.text)) {
            name.error = REGISTER_INVALID_NAME
        } else {
            name.error = null
        }
    }

    fun setErrorIfEmailIsInvalid(email: TextInputEditText): Boolean {
        return if (!InputValidator.isEmailValid(email.text)) {
            email.error = REGISTER_INVALID_EMAIL
            false
        } else {
            email.error = null
            true
        }
    }

    fun setErrorIfPasswordIsInvalid(password: TextInputEditText): Boolean {
        return if (!InputValidator.isPasswordValid(password.text)) {
            password.error = REGISTER_INVALID_PASSWORD
            false
        } else {
            password.error = null
            true
        }
    }
}
