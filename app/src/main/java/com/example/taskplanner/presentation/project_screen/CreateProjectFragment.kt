package com.example.taskplanner.presentation.project_screen

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.CreateProjectFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
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
        observeStartDate(viewModel)
        setListeners(viewModel)
        observeEndDate(viewModel)
        observeScreenState(viewModel)
    }

    private fun setListeners(viewModel: CreateProjectViewModel) {
        with(binding) {
            startTimePickerFloatingButton.setOnClickListener {
                setDatePicker {
                    viewModel.setEstimateStartDate(it)
                }
            }
            endTimePickerFloatingButton.setOnClickListener {
                setDatePicker(minDate = viewModel.startDate.value.getDateByTime().time) {
                    viewModel.setEstimateEndDate(it)
                }
            }
            submitFloatingButton.setOnClickListener {
                setInputsForProject(viewModel)
            }
        }
    }

    private fun observeScreenState(viewModel: CreateProjectViewModel) {
        flowObserver(viewModel.createProjectScreenState) { state ->
            binding.loadingProgressBar.isVisible = state.isLoading
            if (state.success != null) {
                findNavController().navigate(R.id.action_createProjectFragment_to_homeFragment)
            } else if (state.errorText != null) {
                createSnackBar(state.errorText) {
                    snackAction(Color.RED, action = getString(string.ok)) {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun observeEndDate(viewModel: CreateProjectViewModel) {
        liveDataObserver(viewModel.endDate) {
            binding.endTimeTextView.text = getString(string.txt_estimate_end_date, it)
        }
    }

    private fun observeStartDate(viewModel: CreateProjectViewModel) {
        liveDataObserver(viewModel.startDate) {
            binding.startTimeTextView.text = getString(string.txt_estimate_start_date, it)
        }
    }

    private fun setInputsForProject(viewModel: CreateProjectViewModel) {
        with(binding) {
            viewModel.setProject(
                titleEditText.text.toString(), descriptionEditText.text.toString()
            )
        }
    }

    private fun setFabIconChangeListener() {
        with(binding) {
            root.setActionOnSpecifiedProgress(TRANSITION_DEF_PROGRESS,
                {
                    timePickerFloatingButton.setDrawableImage(
                        requireContext(),
                        R.drawable.ic_add_clock
                    )
                },
                {
                    timePickerFloatingButton.setDrawableImage(requireContext(), R.drawable.ic_close)
                })
        }
    }

    companion object {
        private const val TRANSITION_DEF_PROGRESS = 0.4f
    }
}