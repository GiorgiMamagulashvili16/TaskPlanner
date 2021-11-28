package com.example.taskplanner.presentation.authorization.registration_screen

import android.Manifest
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.RegistrationFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias string = R.string

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationFragmentBinding, RegistrationViewModel>() {


    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun inflateFragment(): Inflate<RegistrationFragmentBinding> {
        return RegistrationFragmentBinding::inflate
    }

    override fun getVmClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun onBindViewModel(viewModel: RegistrationViewModel) {
        setJobTextOnScreen()
        observeSignUp(viewModel)
        setListeners(viewModel)
        observeProfileImageUri(viewModel)
        setImagePickerLauncher(viewModel)
    }

    private fun observeSignUp(viewModel: RegistrationViewModel) {
        observeData(viewModel.screenState) {
            when (it) {
                is AuthScreenState.Success -> {
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    binding.loadingProgressBar.isVisible = it.isLoading
                }
                is AuthScreenState.Error -> {
                    createSnackBar(it.errorText!!) {
                        snackAction(Color.RED, action = getString(string.ok)) {
                            dismiss()
                        }
                    }
                    binding.loadingProgressBar.isVisible = it.isLoading
                }
                is AuthScreenState.Loading -> {
                    binding.loadingProgressBar.isVisible = it.isLoading
                }
            }
        }
    }

    private fun observeProfileImageUri(viewModel: RegistrationViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageUri.collect {
                binding.userPictureImageView.setImageURI(it)
            }
        }
    }

    private fun setListeners(viewModel: RegistrationViewModel) {
        binding.addImageTextView.setOnClickListener {
            mediaPermissionRequest(
                positiveAction = {
                    openMedia()
                },
                mediaPermissionCheckerAction = {
                    requestMediaPermissions(mediaPermissionChecker)
                },
            )
        }
        binding.signUpButton.setOnClickListener {
            signUp(viewModel)
        }
    }

    private fun openMedia() {
        imagePickerLauncher.launch("image/*")
    }

    private val mediaPermissionChecker =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perm ->
            if (perm[Manifest.permission.CAMERA] == true && perm[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                perm[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
            ) {
                openMedia()
            }
        }

    private fun setImagePickerLauncher(viewModel: RegistrationViewModel) {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    viewModel.setImageUri(it)
                }
            }
    }


    private fun signUp(viewModel: RegistrationViewModel) {
        viewModel.imageUri.value?.let {
            with(binding) {
                viewModel.signUp(
                    username = usernameInputLayout.editText.text.toString(),
                    password = passwordEditText.text.toString(),
                    email = emailInputLayout.editText.text.toString(),
                    job = jobInputLayout.editText.text.toString(),
                    it
                )
            }
        }?: createSnackBar(getString(string.txt_please_choose_image)) {
            snackAction(action = getString(string.ok)) {
                dismiss()
            }
        }
    }

    private fun setJobTextOnScreen() {
        binding.jobTextView.setTextWithMultipleColor(
            mutableListOf(
                getString(string.txt_add_my),
                getString(string.profession)
            ),
            mutableListOf(
                R.color.black,
                R.color.bg_blue
            )
        )
    }
}