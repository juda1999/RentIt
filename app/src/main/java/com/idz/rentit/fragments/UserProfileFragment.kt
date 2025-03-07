package com.idz.rentit.fragments

import android.content.Context
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.idz.rentIt.R
import com.idz.rentIt.databinding.FragmentUserProfileBinding
import com.idz.rentit.GuestsActivity
import com.idz.rentit.IntroActivity
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.User
import com.idz.rentit.viewModels.UserProfileFragmentViewModel
import com.squareup.picasso.Picasso
import java.util.Objects

class UserProfileFragment : Fragment() {
    private lateinit var viewBindings: FragmentUserProfileBinding
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
        this.viewModel = ViewModelProvider(this)[UserProfileFragmentViewModel::class.java]
    }

    private fun initializeUser() {
        this.userId = Repository.repositoryInstance.getAuthModel().currentUserUid
        Repository.repositoryInstance.getFirebaseModel().userExecutor
            .getUserById(this.userId) { user: User ->
                if (Objects.nonNull(user)) {
                    viewModel?.setUser(user)
                }
                displayUserDetails()
            }
    }

    private fun displayUserDetails() {
        if (Objects.nonNull(viewModel?.getUser())) {
            viewBindings.userProfileFragmentFirstnameInputEt.setText(
                viewModel?.getUser()?.firstName ?: "anonymous"
            )
            viewBindings.userProfileFragmentLastnameInputEt.setText(
                viewModel?.getUser()?.lastName ?: "anonymous"
            )
            viewBindings.userProfileFragmentEmailInputEt.setText(
                viewModel?.getUser()?.email
            )
            loadUserProfileImage()
            setUserProfilePropertiesState()
        }
    }

    private fun loadUserProfileImage() {
        if (Objects.nonNull(viewModel?.getUser()?.imageUrl)) {
            Picasso.get().load(viewModel?.getUser()?.imageUrl)
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
        viewBindings.userProfileFragmentEditProfileBtn.setOnClickListener { view ->
            val action: NavDirections =
                UserProfileFragmentDirections.actionUserProfileFragmentToEditUserProfileFragment(this.userId!!)
            findNavController(view).navigate(action)
        }
    }

      private fun configureMenuOptions(view: View) {
        val parentActivity: FragmentActivity = activity as FragmentActivity
        parentActivity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.removeItem(R.id.userProfileFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.propertyHomeFragment) {
                    findNavController(view).popBackStack()
                    return true
                } else {
                    if (menuItem.itemId == R.id.addPropertyFragment) {
                        findNavController(view).navigate(R.id.addPropertyFragment)
                        return true
                    }
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
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED)
    }

    protected fun startIntroActivity() {
        activity?.let {
            val introActivityIntent = Intent(it, GuestsActivity::class.java)
            startActivity(introActivityIntent)
            it.finish()
        }
    }
}