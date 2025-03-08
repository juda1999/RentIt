package com.idz.rentit.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class UpdateUserProfileDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(
            requireActivity()
        )
        builder.setTitle("Update user profile")
        builder.setMessage("Updating user profile operation was completed successfully")
        builder.setPositiveButton(
            "OK"
        ) { dialogInterface: DialogInterface?, i: Int ->
            Toast.makeText(
                context,
                "User profile has been updated",
                Toast.LENGTH_LONG
            )
                .show()
        }
        return builder.create()
    }
}