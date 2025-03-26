package com.idz.rentit.utils

import android.text.Editable
import android.util.Patterns

object InputValidator {
    fun isFirstNameValid(text: Editable?): Boolean {
        return !isFieldEmpty(text)
    }

    fun isLastNameValid(text: Editable?): Boolean {
        return !isFieldEmpty(text)
    }

    fun isPasswordValid(text: Editable?): Boolean {
        return (text != null && text.length >= 8)
    }


    fun isEmailValid(text: Editable?): Boolean {
        return (text != null && Patterns.EMAIL_ADDRESS.matcher(text).matches())
    }

    fun isFieldEmpty(text: Editable?): Boolean {
        return (text == null || text.length == 0)
    }
}