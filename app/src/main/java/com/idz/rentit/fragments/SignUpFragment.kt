package com.idz.rentit.fragments

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentSignupBinding
import com.idz.rentit.MainActivity
import com.idz.rentit.constants.AuthConstants
import com.idz.rentit.constants.UserConstants
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.User
import com.idz.rentit.utils.InputValidator
import com.idz.rentit.utils.UserUtils
import com.idz.rentit.viewmodels.SignUpFragmentViewModel
import java.util.Objects


class SignUpFragment : Fragment() {
    private lateinit var viewBindings: FragmentSignupBinding
    private lateinit var viewModel: SignUpFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCameraLauncher(registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { result ->
            if (result != null) {
                viewBindings.signUpFragmentImg.setImageBitmap(result)
                viewBindings.cameraButton.setVisibility(View.GONE)
                viewModel.setProfilePictureSelected(true)
            }
        })
    }

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
        this.viewBindings = FragmentSignupBinding.inflate(inflater, container, false)
        this.configureMenuOptions(viewBindings.getRoot())
        initializeDataMembers()
        setListeners()
        return viewBindings.getRoot()
        return FragmentSignupBinding.inflate(inflater, container, false).root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.viewModel = ViewModelProvider(this).get(
            SignUpFragmentViewModel::class.java
        )
    }

    private fun initializeDataMembers() {
        viewModel.setNavController(NavHostFragment.findNavController(this))
    }

    private fun setListeners() {
        setCameraButtonOnClickListener()
        setRegisterButtonOnClickListener()
        setFirstNameEditTextOnKeyListener()
        setLastNameEditTextOnKeyListener()
        setEmailEditTextOnKeyListener()
        setPasswordEditTextOnKeyListener()
    }

    private fun setCameraButtonOnClickListener() {
        viewBindings.cameraButton.setOnClickListener { view ->
            viewModel.getCameraLauncher()?.launch(null)
        }
    }

    private fun setRegisterButtonOnClickListener() {
        viewBindings.signUpFragmentRegisterBtn.setOnClickListener { view ->
            UserUtils.setErrorIfFirstNameIsInvalid(viewBindings.signUpFragmentFirstNameInputEt)
            UserUtils.setErrorIfLastNameIsInvalid(viewBindings.signUpFragmentLastNameInputEt)
            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signUpFragmentEmailInputEt)
            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signUpFragmentPasswordInputEt)
            if (isFormValid) {
                registerIfValid(view)
            }
        }
    }

    private fun registerIfValid(view: View) {
        Repository.repositoryInstance.getAuthModel()
            .isEmailExists(
                viewBindings.signUpFragmentEmailInputEt.getText().toString(),
                { emailExist: Boolean ->
                    if (emailExist) {
                        viewBindings.signUpFragmentEmailInputEt
                            .setError(AuthConstants.REGISTER_EMAIL_ALREADY_EXIST)
                    } else {
                        this.registerUserProcess()
                    }
                },
                { errorMessage: String? -> Snackbar.make(view, errorMessage ?: "", Snackbar.LENGTH_SHORT).show() })
    }

    private fun registerUserProcess() {
        viewBindings.signUpFragmentRegisterBtn.setEnabled(false)
        val user = User(
            viewBindings.signUpFragmentFirstNameInputEt.getText().toString(),
            viewBindings.signUpFragmentLastNameInputEt.getText().toString(),
            viewBindings.signUpFragmentEmailInputEt.getText().toString()
        )

        if (!viewModel.isProfilePictureSelected()) {
            registerUser(user)
        } else {
            val profileImage =
                (viewBindings.signUpFragmentImg.getDrawable() as BitmapDrawable).bitmap
            uploadUserProfilePhoto(profileImage, user)
        }
    }

    private fun uploadUserProfilePhoto(profileImage: Bitmap, user: User) {
        Repository.repositoryInstance.getFirebaseModel().userExecutor
            .uploadUserImage(profileImage, user.email + UserConstants.USER_IMAGE_PROFILE_EXTENSION) { url: String? ->
                if (Objects.nonNull(url)) {
                    user.imageUrl = url
                }
                registerUser(user)
            }
    }

    private fun registerUser(user: User) {
        Repository.repositoryInstance
            .register(
                { this.navigateToHomePageAfterRegister() },
                user,
                viewBindings.signUpFragmentPasswordInputEt.getText().toString()
            )
    }

    private fun navigateToHomePageAfterRegister() {
        val intent = Intent(
            this.getActivity(),
            MainActivity::class.java
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private val isFormValid: Boolean
        get() = (InputValidator.isFirstNameValid(viewBindings.signUpFragmentFirstNameInputEt.getText()) &&
                InputValidator.isLastNameValid(viewBindings.signUpFragmentLastNameInputEt.getText()) &&
                InputValidator.isEmailValid(viewBindings.signUpFragmentEmailInputEt.getText()) &&
                InputValidator.isPasswordValid(viewBindings.signUpFragmentPasswordInputEt.getText()))

    private fun setFirstNameEditTextOnKeyListener() {
        viewBindings.signUpFragmentFirstNameInputEt.setOnKeyListener { view, i, keyEvent ->
            UserUtils.setErrorIfFirstNameIsInvalid(viewBindings.signUpFragmentFirstNameInputEt)
            false
        }
    }

    private fun setLastNameEditTextOnKeyListener() {
        viewBindings.signUpFragmentLastNameInputEt.setOnKeyListener { view, i, keyEvent ->
            UserUtils.setErrorIfLastNameIsInvalid(viewBindings.signUpFragmentLastNameInputEt)
            false
        }
    }

    private fun setEmailEditTextOnKeyListener() {
        viewBindings.signUpFragmentEmailInputEt.setOnKeyListener { view, i, keyEvent ->
            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signUpFragmentEmailInputEt)
            false
        }
    }

    private fun setPasswordEditTextOnKeyListener() {
        viewBindings.signUpFragmentPasswordInputEt.setOnKeyListener { view, i, keyEvent ->
            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signUpFragmentPasswordInputEt)
            false
        }
    }

    protected fun configureMenuOptions(view: View?) {
        val parentActivity: FragmentActivity? = getActivity()
        parentActivity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment)
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.logoutMenuItem)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().popBackStack()
                    return true
                }
                return false
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED)
    }
}