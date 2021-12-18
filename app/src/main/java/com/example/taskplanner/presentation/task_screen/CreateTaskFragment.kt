package com.example.taskplanner.presentation.task_screen

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.CreateProjectFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateProjectFragmentBinding, CreateTaskViewModel>() {
    override fun inflateFragment(): Inflate<CreateProjectFragmentBinding> {
        return CreateProjectFragmentBinding::inflate
    }

    override fun getVmClass(): Class<CreateTaskViewModel> {
        return CreateTaskViewModel::class.java
    }

    private val args: CreateTaskFragmentArgs by navArgs()
    override fun onBindViewModel(viewModel: CreateTaskViewModel) {
        with(viewModel) {
            setProjectId(args.projectId)
            setProjectStartDate(args.projectStartDate)
            setProjectEndDate(args.projectEndDate)
        }
        observeScreenState(viewModel)
        setListeners(viewModel)
        observeStartDate(viewModel)
        observeEndDate(viewModel)
    }

    private fun setListeners(viewModel: CreateTaskViewModel) {
        with(binding) {
            submitFloatingButton.setOnClickListener {
                setNewTask(viewModel)
            }
            startTimePickerFloatingButton.setOnClickListener {
                setDatePicker(
                    minDate = viewModel.projectStartDate.value,
                    maxDate = viewModel.projectEndDate.value
                ) {
                    viewModel.setEstimateStartDate(it)
                }
            }
            endTimePickerFloatingButton.setOnClickListener {
                setDatePicker(
                    minDate = viewModel.startDate.value ?: viewModel.projectStartDate.value,
                    maxDate = viewModel.projectEndDate.value
                ) {
                    viewModel.setEstimateEndDate(it)
                }
            }
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        setUpScreen()
    }

    private fun observeScreenState(viewModel: CreateTaskViewModel) {
        flowObserver(viewModel.createTaskScreenState) { state ->
            binding.loadingProgressBar.isVisible = state.isLoading
            if (state.success != null) {
                findNavController().navigateUp()
            } else if (state.errorText != null) {
                createSnackBar(state.errorText, Snackbar.LENGTH_INDEFINITE) {
                    snackAction(Color.RED, getString(string.ok)) {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun observeStartDate(viewModel: CreateTaskViewModel) {
        liveDataObserver(viewModel.startDate) {
            binding.startTimeTextView.text =
                getString(string.txt_estimate_start_date, it.getTimeByMillis())
        }
    }

    private fun observeEndDate(viewModel: CreateTaskViewModel) {
        liveDataObserver(viewModel.endDate) {
            binding.endTimeTextView.text =
                getString(string.txt_estimate_end_date, it.getTimeByMillis())
        }
    }

    private fun setNewTask(viewModel: CreateTaskViewModel) {
        with(binding) {
            viewModel.setTask(titleEditText.text.toString(), descriptionEditText.text.toString())
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

    private fun setUpScreen() {
        with(binding) {
            titleInputLayout.hint = getString(string.task_title)
            descriptionInputLayout.hint = getString(string.task_description)
            createNewProjectTextView.text = getString(string.create_new_task)
        }
        setFabIconChangeListener()
    }

    companion object {
        private const val TRANSITION_DEF_PROGRESS = 0.4f
    }

}