package com.example.taskplanner.presentation.authorization.registration_screen

import com.example.taskplanner.databinding.RegistrationFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding,RegistrationViewModel>() {
    override fun inflateFragment(): Inflate<RegistrationFragmentBinding> {
        return RegistrationFragmentBinding::inflate
    }

    override fun getVmClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun onBindViewModel(viewModel: RegistrationViewModel) {

    }

}