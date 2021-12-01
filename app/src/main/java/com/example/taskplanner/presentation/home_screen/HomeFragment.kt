package com.example.taskplanner.presentation.home_screen

import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
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
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createProjectFragment)
        }
    }
}