package com.example.taskplanner.presentation.authorization.registration_screen

import android.Manifest
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.util.PermissionManager
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.RegistrationFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

typealias string = R.string

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<RegistrationFragmentBinding, RegistrationViewModel>() {


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
        flowObserver(viewModel.registerScreenState) { state ->
            with(binding) {
                loadingProgressBar.isVisible = state.isLoading
                if (state.success != null)
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
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

    private fun observeProfileImageUri(viewModel: RegistrationViewModel) {
        flowObserver(viewModel.imageUri) {
            binding.userPictureImageView.setImageURI(it)
        }
    }

    private fun setListeners(viewModel: RegistrationViewModel) {
        binding.addImageTextView.setOnClickListener {
            PermissionManager(requireContext(), requireActivity()).mediaPermissionRequest(
                positiveAction = {
                    openMedia()
                },
                mediaPermissionCheckerAction = {
                    requestMediaPermissions(mediaPermissionChecker)
                },
                actionWhenPermissionIsDenied = {
                    createSnackBar(
                        getString(string.app_need_this_permission),
                        length = Snackbar.LENGTH_INDEFINITE
                    ) {
                        snackAction(action = getString(string.ok)) {
                            requestMediaPermissions(mediaPermissionChecker)
                        }
                    }
                }
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
        checkMediaPermissions { openMedia() }

    private fun setImagePickerLauncher(viewModel: RegistrationViewModel) {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    viewModel.setImageUri(it)
                }
            }
    }

    private fun requestMediaPermissions(request: ActivityResultLauncher<Array<String>>) {
        request.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    private fun signUp(viewModel: RegistrationViewModel) {
        viewModel.imageUri.value?.let {
            with(binding) {
                viewModel.signUp(
                    User(
                        username = usernameInputLayout.editText.text.toString(),
                        email = emailInputLayout.editText.text.toString(),
                        job = jobInputLayout.editText.text.toString(),
                        profileImageUrl = it.toString()
                    ),
                    password = passwordEditText.text.toString()
                )
            }
        } ?: createSnackBar(getString(string.txt_please_choose_image)) {
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