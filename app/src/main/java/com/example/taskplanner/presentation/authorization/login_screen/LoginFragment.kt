package com.example.taskplanner.presentation.authorization.login_screen

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.createSnackBar
import com.example.taskplanner.data.util.extension.flowObserver
import com.example.taskplanner.data.util.extension.observeData
import com.example.taskplanner.data.util.extension.snackAction
import com.example.taskplanner.databinding.LoginFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {
    override fun inflateFragment(): Inflate<LoginFragmentBinding> {
        return LoginFragmentBinding::inflate
    }

    override fun getVmClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onBindViewModel(viewModel: LoginViewModel) {
        setListeners(viewModel)
        observeLoginResponse(viewModel)
    }

    private fun observeLoginResponse(viewModel: LoginViewModel) {
        flowObserver(viewModel.screenState) { state ->
            with(binding) {
                loadingProgressBar.isVisible = state.isLoading
                if (state.isSuccess)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                else if (state.errorText != null) {
                    createSnackBar(state.errorText) {
                        snackAction(Color.RED, action = getString(string.ok)) {
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun logIn(viewModel: LoginViewModel) {
        with(binding) {
            viewModel.logIn(
                password = passwordEditText.text.toString(),
                email = emailInputLayout.editText.text.toString()
            )
        }
    }

    private fun setListeners(viewModel: LoginViewModel) {
        with(binding) {
            logInButton.setOnClickListener {
                logIn(viewModel)
            }
            signUpTextView.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }
}