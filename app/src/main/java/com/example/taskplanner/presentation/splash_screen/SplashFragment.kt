package com.example.taskplanner.presentation.splash_screen

import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class SplashFragment : BaseFragment<SplashFragmentBinding,SplashViewModel>() {
    override fun inflateFragment(): Inflate<SplashFragmentBinding> = SplashFragmentBinding::inflate

    override fun getVmClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onBindViewModel(viewModel: SplashViewModel) {
    }

}