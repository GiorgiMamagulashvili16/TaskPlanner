package com.example.taskplanner.presentation.splash_screen

import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.setAfterAnimOver
import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {
    override fun inflateFragment(): Inflate<SplashFragmentBinding> = SplashFragmentBinding::inflate

    override fun getVmClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onBindViewModel(viewModel: SplashViewModel) {
        binding.root.setAfterAnimOver {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

}