package com.idz.rentit.repository.firebase

import com.idz.rentit.listeners.authentication.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import java.util.Objects

class AuthModel {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, registerListener: (String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful && task.result.user != null) {
                    registerListener(task.result.user!!.uid)
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { task: AuthResult? -> onSuccessListener() }
            .addOnFailureListener { command: Exception ->
                onFailureListener(command.message ?: "")
            }
    }

    fun logout(logoutListener: () -> Unit) {
        firebaseAuth.signOut()
        logoutListener()
    }

    val isSignedIn: Boolean
        get() {
            val currentUser = firebaseAuth.currentUser
            return (Objects.nonNull(currentUser))
        }

    val currentUserUid: String?
        get() {
            if (isSignedIn) {
                return firebaseAuth.currentUser!!.uid
            }
            return null
        }

    fun isEmailExists(
        email: String,
        onSuccessListenerComplete: (Boolean) -> Unit,
        onFailureListenerComplete: (String?) -> Unit
    ) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnSuccessListener { task: SignInMethodQueryResult ->
                if (task.signInMethods != null) {
                    val isNewUser = task.signInMethods!!.isEmpty()
                    onSuccessListenerComplete(!isNewUser)
                }
            }
            .addOnFailureListener { command: Exception ->
                onFailureListenerComplete(command.message)
            }
    }
}