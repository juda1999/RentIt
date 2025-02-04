package com.idz.rentit.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation.findNavController
import com.idz.rentIt.R


class SignUpFragment : Fragment() {
//    private var viewBindings: FragmentSignUpBinding? = null
//    private var viewModel: SignUpFragmentViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.setCameraLauncher(registerForActivityResult(
//            TakePicturePreview()
//        ) { result ->
//            if (Objects.nonNull(result)) {
//                viewBindings.signUpFragmentImg.setImageBitmap(result)
//                viewBindings.cameraButton.setVisibility(View.GONE)
//                viewModel.setProfilePictureSelected(true)
//            }
//        })
    }
//
//    override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//    ): View {
//        this.viewBindings = FragmentSignUpBinding.inflate(inflater, container, false)
//        this.configureMenuOptions(viewBindings.getRoot())
//        initializeDataMembers()
//        setListeners()
//        return viewBindings.getRoot()
//        return FragmentSignUpBinding.inflate(inflater, container, false).root
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        this.viewModel = ViewModelProvider(this).get<T>(
//            SignUpFragmentViewModel::class.java
//        )
//    }
//
//    private fun initializeDataMembers() {
//        viewModel.setNavController(NavHostFragment.findNavController(this))
//    }
//
//    private fun setListeners() {
//        setCameraButtonOnClickListener()
//        setRegisterButtonOnClickListener()
//        setFirstNameEditTextOnKeyListener()
//        setLastNameEditTextOnKeyListener()
//        setEmailEditTextOnKeyListener()
//        setPasswordEditTextOnKeyListener()
//    }
//
//    private fun setCameraButtonOnClickListener() {
//        viewBindings.cameraButton.setOnClickListener { view ->
//            viewModel.getCameraLauncher()
//                .launch(null)
//        }
//    }
//
//    private fun setRegisterButtonOnClickListener() {
//        viewBindings.signUpFragmentRegisterBtn.setOnClickListener { view ->
//            UserUtils.setErrorIfFirstNameIsInvalid(viewBindings.signUpFragmentFirstNameInputEt)
//            UserUtils.setErrorIfLastNameIsInvalid(viewBindings.signUpFragmentLastNameInputEt)
//            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signUpFragmentEmailInputEt)
//            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signUpFragmentPasswordInputEt)
//            if (isFormValid) {
//                registerIfValid(view)
//            }
//        }
//    }
//
//    private fun registerIfValid(view: View) {
//        Repository.getRepositoryInstance().getAuthModel()
//            .isEmailExists(
//                viewBindings.signUpFragmentEmailInputEt.getText().toString(),
//                { emailExist ->
//                    if (emailExist) {
//                        viewBindings.signUpFragmentEmailInputEt
//                            .setError(REGISTER_EMAIL_ALREADY_EXIST)
//                    } else {
//                        this.registerUserProcess()
//                    }
//                },
//                { errorMessage -> Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show() })
//    }
//
//    private fun registerUserProcess() {
//        viewBindings.signUpFragmentRegisterBtn.setEnabled(false)
//        val user = User(
//            viewBindings.signUpFragmentFirstNameInputEt.getText().toString(),
//            viewBindings.signUpFragmentLastNameInputEt.getText().toString(),
//            viewBindings.signUpFragmentEmailInputEt.getText().toString()
//        )
//
//        if (!viewModel.isProfilePictureSelected()) {
//            registerUser(user)
//        } else {
//            val profileImage =
//                (viewBindings.signUpFragmentImg.getDrawable() as BitmapDrawable).bitmap
//            uploadUserProfilePhoto(profileImage, user)
//        }
//    }
//
//    private fun uploadUserProfilePhoto(profileImage: Bitmap, user: User) {
//        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
//            .uploadUserImage(profileImage, user.getEmail() + USER_IMAGE_PROFILE_EXTENSION) { url ->
//                if (Objects.nonNull(url)) {
//                    user.setImageUrl(url)
//                }
//                registerUser(user)
//            }
//    }
//
//    private fun registerUser(user: User) {
//        Repository.getRepositoryInstance()
//            .register(
//                { this.navigateToHomePageAfterRegister() },
//                user,
//                viewBindings.signUpFragmentPasswordInputEt.getText().toString()
//            )
//    }
//
//    private fun navigateToHomePageAfterRegister() {
//        val intent = Intent(
//            this.getActivity(),
//            MainActivity::class.java
//        )
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        startActivity(intent)
//    }
//
//    private val isFormValid: Boolean
//        get() = (InputValidator.isFirstNameValid(viewBindings.signUpFragmentFirstNameInputEt.getText()) &&
//                InputValidator.isLastNameValid(viewBindings.signUpFragmentLastNameInputEt.getText()) &&
//                InputValidator.isEmailValid(viewBindings.signUpFragmentEmailInputEt.getText()) &&
//                InputValidator.isPasswordValid(viewBindings.signUpFragmentPasswordInputEt.getText()))
//
//    private fun setFirstNameEditTextOnKeyListener() {
//        viewBindings.signUpFragmentFirstNameInputEt.setOnKeyListener { view, i, keyEvent ->
//            UserUtils.setErrorIfFirstNameIsInvalid(viewBindings.signUpFragmentFirstNameInputEt)
//            false
//        }
//    }
//
//    private fun setLastNameEditTextOnKeyListener() {
//        viewBindings.signUpFragmentLastNameInputEt.setOnKeyListener { view, i, keyEvent ->
//            UserUtils.setErrorIfLastNameIsInvalid(viewBindings.signUpFragmentLastNameInputEt)
//            false
//        }
//    }
//
//    private fun setEmailEditTextOnKeyListener() {
//        viewBindings.signUpFragmentEmailInputEt.setOnKeyListener { view, i, keyEvent ->
//            UserUtils.setErrorIfEmailIsInvalid(viewBindings.signUpFragmentEmailInputEt)
//            false
//        }
//    }
//
//    private fun setPasswordEditTextOnKeyListener() {
//        viewBindings.signUpFragmentPasswordInputEt.setOnKeyListener { view, i, keyEvent ->
//            UserUtils.setErrorIfPasswordIsInvalid(viewBindings.signUpFragmentPasswordInputEt)
//            false
//        }
//    }
//
//    protected override fun configureMenuOptions(view: View?) {
//        val parentActivity: FragmentActivity = getActivity()
//        parentActivity.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menu.removeItem(R.id.userCommentAdditionFragment)
//                menu.removeItem(R.id.userProfileFragment)
//                menu.removeItem(R.id.logoutMenuItem)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                if (menuItem.itemId == R.id.home) {
//                    findNavController(view!!).popBackStack()
//                    return true
//                }
//                return false
//            }
//        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED)
//    }
}