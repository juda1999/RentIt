package com.idz.rentit.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.idz.rentit.MainActivity
import com.idz.rentit.repository.Repository
import com.idz.rentit.utils.UserUtils
import com.google.android.material.snackbar.Snackbar
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentSigninBinding
import com.idz.rentit.viewModels.SignInFragmentViewModel


class SignInFragment : Fragment() {
    private lateinit var viewBindings: FragmentSigninBinding
    private lateinit var viewModel: SignInFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBindings = FragmentSigninBinding.inflate(inflater, container, false)
        configureMenuOptions(viewBindings.root)
        initializeDataMembers()
        setListeners()
        return viewBindings.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(SignInFragmentViewModel::class.java)
    }

    private fun initializeDataMembers() {
        viewModel.setNavController(NavHostFragment.findNavController(this))
    }

    private fun setListeners() {
        setLoginButtonOnClickListener()
        setRegisterButtonOnClickListener()
        setEmailEditTextOnKeyListener()
        setPasswordEditTextOnKeyListener()
    }

    private fun setLoginButtonOnClickListener() {
        viewBindings.signInFragmentLoginBtn.setOnClickListener { view ->
            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signInFragmentEmailInputEt)
            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signInFragmentPasswordInputEt)

            if (isFormValid()) {
                viewBindings.signInFragmentLoginBtn.isEnabled = false
                viewBindings.signInFragmentRegisterBtn.isEnabled = false
                Repository.repositoryInstance.getAuthModel().login(
                    viewBindings.signInFragmentEmailInputEt.text.toString(),
                    viewBindings.signInFragmentPasswordInputEt.text.toString(),
                    {
                        val intent = Intent(activity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                    },
                    { errorMessage: String ->
                        Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show()
                        viewBindings.signInFragmentLoginBtn.isEnabled = true
                        viewBindings.signInFragmentRegisterBtn.isEnabled = true
                    })
            }
            }
    }

    private fun isFormValid(): Boolean {
        return UserUtils.setErrorIfEmailIsInvalid(viewBindings.signInFragmentEmailInputEt) &&
                UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signInFragmentPasswordInputEt)
    }

    private fun setRegisterButtonOnClickListener() {
        viewBindings.signInFragmentRegisterBtn.setOnClickListener {
            val action: NavDirections =
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            viewModel.getNavController()?.navigate(action)
        }
    }

    private fun setEmailEditTextOnKeyListener() {
        viewBindings.signInFragmentEmailInputEt.setOnKeyListener { _, _, _ ->
            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signInFragmentEmailInputEt)
            false
        }
    }

    private fun setPasswordEditTextOnKeyListener() {
        viewBindings.signInFragmentPasswordInputEt.setOnKeyListener { _, _, _ ->
            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signInFragmentPasswordInputEt)
            false
        }
    }

     fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = requireActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.logoutMenuItem)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack()
                    true
                } else {
                    false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
