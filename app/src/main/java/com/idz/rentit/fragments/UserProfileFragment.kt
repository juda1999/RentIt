package com.idz.rentit.fragments

import android.R
import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import com.idz.rentit.databinding.FragmentUserProfileBinding
import com.idz.rentit.repository.Repository
import com.idz.rentit.viewmodels.UserProfileFragmentViewModel
import com.squareup.picasso.Picasso
import java.util.Objects

class UserProfileFragment : Fragment() {
    private var viewBindings: FragmentUserProfileBinding? = null
    private var userId: String? = null
    private var viewModel: UserProfileFragmentViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.viewBindings = FragmentUserProfileBinding.inflate(inflater, container, false)
        this.configureMenuOptions(viewBindings.getRoot())
        initializeUser()
        activateButtonsListeners()
        return viewBindings.getRoot()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.viewModel = ViewModelProvider(this).get<T>(
            UserProfileFragmentViewModel::class.java
        )
    }

    private fun initializeUser() {
        this.userId = Repository.getRepositoryInstance().getAuthModel().getCurrentUserUid()
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
            .getUserById(this.userId) { user ->
                if (Objects.nonNull(user)) {
                    viewModel.setUser(user)
                }
                displayUserDetails()
            }
    }

    private fun displayUserDetails() {
        if (Objects.nonNull(viewModel.getUser())) {
            viewBindings.userProfileFragmentFirstnameInputEt.setText(
                viewModel.getUser().getFirstName()
            )
            viewBindings.userProfileFragmentLastnameInputEt.setText(
                viewModel.getUser().getLastName()
            )
            viewBindings.userProfileFragmentEmailInputEt.setText(
                viewModel.getUser().getEmail()
            )
            loadUserProfileImage()
            setUserProfilePropertiesState()
        }
    }

    private fun loadUserProfileImage() {
        if (Objects.nonNull(viewModel.getUser().getImageUrl())) {
            Picasso.get().load(viewModel.getUser().getImageUrl())
                .placeholder(R.drawable.avatar)
                .into(viewBindings.userProfileFragmentImg)
        } else {
            viewBindings.userProfileFragmentImg.setImageResource(R.drawable.avatar)
        }
    }

    private fun setUserProfilePropertiesState() {
        viewBindings.userProfileFragmentFirstnameInputEt.setFocusable(false)
        viewBindings.userProfileFragmentLastnameInputEt.setFocusable(false)
        viewBindings.userProfileFragmentEmailInputEt.setFocusable(false)
    }

    private fun activateButtonsListeners() {
        viewBindings.userProfileFragmentCommentsBtn.setOnClickListener { view ->
            val action: NavDirections =
                UserProfileFragmentDirections
                    .actionProfileFragmentToUserCommentListFragment(this.userId)
            findNavController(view).navigate(action)
        }
        viewBindings.userProfileFragmentEditProfileBtn.setOnClickListener { view ->
            val action: NavDirections =
                UserProfileFragmentDirections
                    .actionUserProfileFragmentToUserProfileEditionFragment(this.userId)
            findNavController(view).navigate(action)
        }
    }

    protected override fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = getActivity()
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment)
                menu.removeItem(R.id.userProfileFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.home) {
                    findNavController(view).popBackStack()
                    return true
                } else {
                    if (Objects.nonNull(view)) {
                        Repository.getRepositoryInstance().getAuthModel()
                            .logout { startIntroActivity() }
                        return true
                    }
                }
                return false
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED)
    }
}