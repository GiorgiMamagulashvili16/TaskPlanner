package com.example.taskplanner.presentation.task_screen

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.Constants.TRANSITION_DEF_PROGRESS
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.CreateTaskFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateTaskFragmentBinding, CreateTaskViewModel>() {
    override fun inflateFragment(): Inflate<CreateTaskFragmentBinding> {
        return CreateTaskFragmentBinding::inflate
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
                    minDate = viewModel.projectStartDate.value.getDateByTime().time,
                    maxDate = viewModel.projectEndDate.value.getDateByTime().time
                ) {
                    viewModel.setEstimateStartDate(it)
                }
            }
            endTimePickerFloatingButton.setOnClickListener {
                setDatePicker(
                    minDate = viewModel.startDate.value.getDateByTime().time,
                    maxDate = viewModel.projectEndDate.value.getDateByTime().time
                ) {
                    viewModel.setEstimateEndDate(it)
                }
            }
            backButton.setOnClickListener {
                findNavController().navigate(R.id.action_createTaskFragment_to_projectDetailFragment)
            }
        }
        setUpScreen()
    }

    private fun observeScreenState(viewModel: CreateTaskViewModel) {
        flowObserver(viewModel.createTaskScreenState) { state ->
            binding.loadingProgressBar.isVisible = state.isLoading
            if (state.success != null) {
                CreateTaskFragmentDirections.actionCreateTaskFragmentToProjectDetailFragment(
                    viewModel.projectId.value
                ).also { findNavController().navigate(it) }
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
            binding.startTimeTextView.text = getString(string.txt_estimate_start_date, it)
        }
    }

    private fun observeEndDate(viewModel: CreateTaskViewModel) {
        liveDataObserver(viewModel.endDate) {
            binding.endTimeTextView.text = getString(string.txt_estimate_end_date, it)
        }
    }

    private fun setNewTask(viewModel: CreateTaskViewModel) {
        with(binding) {
            val task = Task(
                projectId = viewModel.projectId.value,
                taskTitle = titleEditText.text.toString(),
                taskDescription = descriptionEditText.text.toString(),
                startTime = viewModel.startDate.value,
                endTime = viewModel.endDate.value,
            )
            viewModel.setTask(task)
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
}