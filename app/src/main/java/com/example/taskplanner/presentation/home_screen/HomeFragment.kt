package com.example.taskplanner.presentation.home_screen

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.extension.createSnackBar
import com.example.taskplanner.data.util.extension.flowObserver
import com.example.taskplanner.data.util.extension.loadImage
import com.example.taskplanner.data.util.extension.snackAction
import com.example.taskplanner.databinding.HomeFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {
    override fun inflateFragment(): Inflate<HomeFragmentBinding> {
        return HomeFragmentBinding::inflate
    }

    override fun getVmClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    private val projectAdapter by lazy { ProjectsAdapter() }

    override fun onBindViewModel(viewModel: HomeViewModel) {
        viewModel.getCurrentUserData()
        setListeners()
        observeScreenState(viewModel)
    }

    private fun setListeners() {
        projectAdapter.onProjectClick = { projectId ->
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                HomeFragmentDirections.actionHomeFragmentToProjectDetailFragment(projectId).also {
                    findNavController().navigate(it)
                }
            }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createProjectFragment)
        }
    }

    private fun observeScreenState(viewModel: HomeViewModel) {
        flowObserver(viewModel.homeScreenState) { state ->
            binding.loadingProgressBar.isVisible = state.isLoading
            if (state.success != null) {
                setUserData(state.success)
            } else if (state.errorText != null) {
                createSnackBar(state.errorText) {
                    snackAction(Color.RED, getString(string.ok)) {
                        dismiss()
                    }
                }
            }

        }
    }

    private fun setUserData(user: User) {
        with(binding) {
            profileImageView.loadImage(user.profileImageUrl!!)
            jobTextView.text = user.job
            usernameTextView.text = user.username
        }
        initProjectRecycleView()
        binding.emptyProjectTextView.isVisible = user.projects?.isEmpty()!!
        projectAdapter.submitList(user.projects)
    }

    private fun initProjectRecycleView() {
        with(binding.projectRecycleView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = projectAdapter
        }
    }
}