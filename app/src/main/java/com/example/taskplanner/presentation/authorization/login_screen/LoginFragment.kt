package com.example.taskplanner.presentation.authorization.login_screen

import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.LoginFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class LoginFragment : BaseFragment<LoginFragmentBinding,LoginViewModel>() {
    override fun inflateFragment(): Inflate<LoginFragmentBinding> {
        return  LoginFragmentBinding::inflate
    }

    override fun getVmClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onBindViewModel(viewModel: LoginViewModel) {
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding.logInButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }
}