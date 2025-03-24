package com.idz.rentit.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentEditUserProfileBinding
import com.idz.rentit.constants.UserConstants.USER_IMAGE_PROFILE_EXTENSION
import com.idz.rentit.repository.Repository
import com.idz.rentit.utils.InputValidator
import com.idz.rentit.utils.UserUtils
import com.idz.rentit.viewModels.EditUserProfileFragmentViewModel
import com.squareup.picasso.Picasso
import java.util.Objects

class EditUserProfileFragment : PropertyBaseFragment() {
    private var viewBindings: FragmentEditUserProfileBinding? = null
    private var userId: String? = null
    private var viewModel: EditUserProfileFragmentViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.userId =
            EditUserProfileFragmentArgs.fromBundle(requireArguments()).userId
        viewModel?.cameraLauncher = (registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { result ->
            if (Objects.nonNull(result)) {
                viewBindings?.userProfileEditionFragmentImg?.setImageBitmap(result)
                viewModel?.isProfilePictureSelected = (true)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.viewBindings = FragmentEditUserProfileBinding.inflate(inflater, container, false)
        this.configureMenuOptions(viewBindings!!.getRoot())
        initializeUser()
        activateButtonsListeners()
        return viewBindings!!.getRoot()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.viewModel = ViewModelProvider(this).get(
            EditUserProfileFragmentViewModel::class.java
        )
    }

    private fun initializeUser() {
        Repository.repositoryInstance.getFirebaseModel().userExecutor
            .getUserById(this.userId) { user ->
                viewModel?.user = user
                displayUserDetails()
            }
    }

    fun displayUserDetails() {
        if (Objects.nonNull(viewModel?.user)) {
            viewBindings?.userProfileEditionFragmentFirstnameInputEt?.setText(
                viewModel?.user?.firstName
            )
            viewBindings?.userProfileEditionFragmentLastnameInputEt?.setText(
                viewModel?.user?.lastName
            )
            loadUserProfileImage()
            setUserPropertiesState()
        }
    }

    private fun loadUserProfileImage() {
        if (Objects.nonNull(viewModel?.user?.imageUrl)) {
            Picasso.get().load(viewModel?.user?.imageUrl)
                .placeholder(R.drawable.avatar)
                .into(viewBindings?.userProfileEditionFragmentImg)
        } else {
            viewBindings?.userProfileEditionFragmentImg?.setImageResource(R.drawable.avatar)
        }
    }

    private fun setUserPropertiesState() {
        viewBindings?.userProfileEditionFragmentFirstnameInputEt?.isFocusable = true
        viewBindings?.userProfileEditionFragmentLastnameInputEt?.isFocusable = true
    }

    private fun activateButtonsListeners() {
        setFirstNameEditTextOnKeyListener()
        setLastNameEditTextOnKeyListener()
        setProfileImageViewOnClickListener()
        viewBindings?.userProfileEditionFragmentSaveBtn?.setOnClickListener { view: View ->
            viewBindings?.userProfileEditionFragmentFirstnameInputEt?.let {
                UserUtils.setErrorIfFirstNameIsInvalid(
                    it
                )
            }
            viewBindings?.userProfileEditionFragmentLastnameInputEt?.let {
                UserUtils.setErrorIfLastNameIsInvalid(
                    it
                )
            }
            if (isFormValid) {
                saveProfile(view)
            }
        }
    }

    private fun saveProfile(view: View) {
        viewBindings?.userProfileEditionFragmentSaveBtn?.setEnabled(false)
        viewModel?.user?.firstName =
            viewBindings?.userProfileEditionFragmentFirstnameInputEt?.getText().toString()
        viewModel?.user?.lastName=
            viewBindings?.userProfileEditionFragmentLastnameInputEt?.getText().toString()


        if (!viewModel?.isProfilePictureSelected!!) {
            updateUser(view)
        } else {
            val profileImage =
                (viewBindings?.userProfileEditionFragmentImg?.getDrawable() as BitmapDrawable).bitmap
            uploadUserProfilePhoto(profileImage, view)
        }
    }

    private fun uploadUserProfilePhoto(profileImage: Bitmap, view: View) {
        Repository.repositoryInstance.getFirebaseModel().userExecutor
            .uploadUserImage(
                profileImage,
                (viewModel?.user?.email ) + USER_IMAGE_PROFILE_EXTENSION
            ) { url ->
                if (Objects.nonNull(url)) {
                    viewModel?.user?.imageUrl = (url)
                }
                updateUser(view)
            }
    }

    private fun updateUser(view: View) {
        Repository.repositoryInstance.getFirebaseModel().userExecutor
            .updateUser(viewModel?.user!!) {
                UpdateUserProfileDialogFragment()
                    .show(requireActivity().getSupportFragmentManager(), "TAG")
                findNavController(view).popBackStack()
            }
    }

    private val isFormValid: Boolean
        get() = (InputValidator.isFirstNameValid(viewBindings?.userProfileEditionFragmentFirstnameInputEt?.getText()) &&
                InputValidator.isLastNameValid(viewBindings?.userProfileEditionFragmentLastnameInputEt?.getText()))

    private fun setProfileImageViewOnClickListener() {
        viewBindings?.userProfileEditionFragmentImgBtn?.setOnClickListener {
            viewModel?.cameraLauncher?.launch(null)
        }
    }

    private fun setFirstNameEditTextOnKeyListener() {
        viewBindings?.userProfileEditionFragmentFirstnameInputEt?.setOnKeyListener { view ,i ,keyEvent->
            UserUtils.setErrorIfFirstNameIsInvalid(viewBindings!!.userProfileEditionFragmentFirstnameInputEt)
            false
        }
    }

    private fun setLastNameEditTextOnKeyListener() {
        viewBindings?.userProfileEditionFragmentLastnameInputEt?.setOnKeyListener { view ,i ,keyEvent->
        UserUtils.setErrorIfLastNameIsInvalid(viewBindings!!.userProfileEditionFragmentLastnameInputEt)
            false
        }
    }

    override fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = requireActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
                menu.removeItem(R.id.addPropertyFragment)
                menu.removeItem(R.id.filterFragment)
                menu.removeItem(R.id.userPropertyFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.propertyHomeFragment) {
                    findNavController(view).popBackStack()
                    return true
                } else {
                    if (menuItem.itemId == R.id.logoutMenuItem) {
                        Repository.repositoryInstance.getAuthModel()
                            .logout {
                                startIntroActivity()
                            }
                        return true
                    }
                    if (menuItem.itemId == android.R.id.home) {
                        findNavController().popBackStack()
                        return true
                    }                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}