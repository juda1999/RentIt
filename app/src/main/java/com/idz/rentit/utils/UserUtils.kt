package com.idz.rentit.utils

import com.example.movieshare.constants.AuthConstants.*
import com.example.movieshare.constants.MovieCommentConstants.MOVIE_COMMENT_RATING_INVALID
import com.example.movieshare.constants.MovieCommentConstants.MOVIE_COMMENT_COMMENT_INVALID
import com.google.android.material.textfield.TextInputEditText

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

    fun setErrorIfBiggerThan(rating: TextInputEditText, maxRating: Int): Boolean {
        val ratingValue = rating.text.toString().toIntOrNull()
        return if (ratingValue == null || ratingValue < 0 || ratingValue > maxRating) {
            rating.error = MOVIE_COMMENT_COMMENT_INVALID
            false
        } else {
            rating.error = null
            true
        }
    }

    fun setErrorIfEmpty(rating: TextInputEditText): Boolean {
        return if (InputValidator.isFieldEmpty(rating.text)) {
            rating.error = MOVIE_COMMENT_RATING_INVALID
            false
        } else {
            rating.error = null
            true
        }
    }
}
