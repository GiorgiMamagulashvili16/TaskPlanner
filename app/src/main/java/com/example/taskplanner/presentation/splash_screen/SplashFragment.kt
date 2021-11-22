package com.example.taskplanner.presentation.splash_screen

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.extension.setAfterAnimOver
import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {
    override fun inflateFragment(): Inflate<SplashFragmentBinding> = SplashFragmentBinding::inflate

    override fun getVmClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onBindViewModel(viewModel: SplashViewModel) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            delay(2000)
//            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//        }

        binding.root.setAfterAnimOver {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

}