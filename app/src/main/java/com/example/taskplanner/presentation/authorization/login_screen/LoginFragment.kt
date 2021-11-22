package com.example.taskplanner.presentation.authorization.login_screen

import androidx.fragment.app.Fragment
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

    }
}