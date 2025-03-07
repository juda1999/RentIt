package com.idz.rentit.fragments


abstract class UserCommentFormFragment : PropertyBaseFragment() {
    protected abstract fun displayUserMovieCommentDetails()

    protected abstract fun setUserCommentPropertiesState()

    protected abstract fun activateButtonsListeners()
}