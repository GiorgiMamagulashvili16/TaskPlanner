package com.example.taskplanner.presentation.home_screen

import com.example.taskplanner.databinding.HomeFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class HomeFragment : BaseFragment<HomeFragmentBinding,HomeViewModel>() {
    override fun inflateFragment(): Inflate<HomeFragmentBinding> {
        return HomeFragmentBinding::inflate
    }

    override fun getVmClass(): Class<HomeViewModel> {
       return HomeViewModel::class.java
    }

    override fun onBindViewModel(viewModel: HomeViewModel) {

    }
}