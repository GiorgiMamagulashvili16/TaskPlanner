package com.example.taskplanner.presentation.new_project_screen

import androidx.appcompat.content.res.AppCompatResources
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.setActionOnSpecifiedProgress
import com.example.taskplanner.databinding.CreateProjectFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProjectFragment : BaseFragment<CreateProjectFragmentBinding, CreateProjectViewModel>() {
    override fun inflateFragment(): Inflate<CreateProjectFragmentBinding> {
        return CreateProjectFragmentBinding::inflate
    }

    override fun getVmClass(): Class<CreateProjectViewModel> {
        return CreateProjectViewModel::class.java
    }

    override fun onBindViewModel(viewModel: CreateProjectViewModel) {
        setFabIconChangeListener()
    }

    private fun setInputsForProject(viewModel: CreateProjectViewModel) {
        with(binding) {
            viewModel.setProject(
                titleEditText.text.toString(),
                descriptionEditText.text.toString(),
                viewModel.startDate,
                viewModel.endDate
            )
        }
    }

    private fun setFabIconChangeListener() {
        with(binding) {
            root.setActionOnSpecifiedProgress(0.4f,
                {
                    timePickerFloatingButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_add_clock
                        )
                    )
                },
                {
                    timePickerFloatingButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_close
                        )
                    )
                })
        }
    }

}