package com.idz.rentit.listeners.authentication

interface AddUserListener {
    fun onComplete()
}

interface IsEmailExistOnFailureListener {
    fun onComplete(errorMessage: String?)
}

interface IsEmailExistOnSuccessListener {
    fun onComplete(emailExist: Boolean)
}

fun interface LoginOnFailureListener {
    fun onComplete(errorMessage: String?)
}

fun interface LoginOnSuccessListener {
    fun onComplete()
}

interface LogoutListener {
    fun onComplete()
}

interface RegisterListener {
    fun onComplete(uid: String?)
}

interface UpdateUserListener {
    fun onComplete()
}

interface UploadUserImageListener {
    fun onComplete(url: String?)
}