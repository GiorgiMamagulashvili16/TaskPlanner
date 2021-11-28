package com.example.taskplanner.presentation.home_screen

import android.util.Log.d
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.extension.observeData
import com.example.taskplanner.databinding.HomeFragmentBinding
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

    override fun onBindViewModel(viewModel: HomeViewModel) {
        viewModel.getCurrentUser()
        observeUserData(viewModel)
    }

    private fun observeUserData(viewModel: HomeViewModel) {
        observeData(viewModel.user) {
            setUserData(it)
        }
    }

    private fun setUserData(user: User) {
        with(binding) {
            usernameTextView.text = user.username
            jobTextView.text = user.job
        }
    }
}