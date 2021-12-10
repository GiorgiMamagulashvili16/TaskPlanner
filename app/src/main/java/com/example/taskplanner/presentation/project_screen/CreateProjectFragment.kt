package com.example.taskplanner.presentation.project_screen

import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.util.Constants.BUNDLE_REQUEST_KEY
import com.example.taskplanner.data.util.Constants.DATE_PICKER_FRAGMENT_TAG
import com.example.taskplanner.data.util.Constants.SELECTED_DATE_STRING_KEY
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.CreateProjectFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.example.taskplanner.presentation.date_picker.DatePickerFragment
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
                setDatePicker {
                    viewModel.setEstimateEndDate(it)
                }
            }
            submitFloatingButton.setOnClickListener {
                setInputsForProject(viewModel)
            }
        }
    }

    private fun observeScreenState(viewModel: CreateProjectViewModel) {
        flowObserver(viewModel.uploadItemState) { state ->
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
                Project(
                    projectTitle = titleEditText.text.toString(),
                    projectDescription = descriptionEditText.text.toString(),
                    startDate = viewModel.startDate.value,
                    endDate = viewModel.endDate.value
                )
            )
        }
    }

    private fun setFabIconChangeListener() {
        with(binding) {
            root.setActionOnSpecifiedProgress(TRANSITION_DEF_PROGRESS,
                {
                    timePickerFloatingButton.setDrawableImage(requireContext(),R.drawable.ic_add_clock)
                },
                {
                    timePickerFloatingButton.setDrawableImage(requireContext(),R.drawable.ic_close)
                })
        }
    }
    companion object{
        private const val TRANSITION_DEF_PROGRESS = 0.4f
    }
}