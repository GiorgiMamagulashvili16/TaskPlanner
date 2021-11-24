package com.example.taskplanner.presentation.authorization.registration_screen

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.setTextWithMultipleColor
import com.example.taskplanner.databinding.RegistrationFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

typealias string = R.string

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding, RegistrationViewModel>() {
    override fun inflateFragment(): Inflate<RegistrationFragmentBinding> {
        return RegistrationFragmentBinding::inflate
    }

    override fun getVmClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun onBindViewModel(viewModel: RegistrationViewModel) {
        binding.professionTextView.setTextWithMultipleColor(
            mutableListOf(
                getString(string.txt_add_my),
                getString(string.profession)
            ),
            mutableListOf(
                R.color.black,
                R.color.bg_blue
            )
        )

        binding.professionInputLayout.editText.doOnTextChanged { text, _, _, _ ->
            binding.submitImageButton.isVisible = text?.isNotBlank() == true
        }
    }
}