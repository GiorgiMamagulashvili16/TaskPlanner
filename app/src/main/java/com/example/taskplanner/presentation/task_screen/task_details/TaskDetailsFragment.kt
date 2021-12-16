package com.example.taskplanner.presentation.task_screen.task_details

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.Constants.TRANSITION_DEF_PROGRESS
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.TaskDetailsFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.example.taskplanner.presentation.project_screen.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : BaseFragment<TaskDetailsFragmentBinding, TaskDetailsViewModel>() {
    override fun inflateFragment(): Inflate<TaskDetailsFragmentBinding> {
        return TaskDetailsFragmentBinding::inflate
    }

    override fun getVmClass(): Class<TaskDetailsViewModel> {
        return TaskDetailsViewModel::class.java
    }

    private val args: TaskDetailsFragmentArgs by navArgs()
    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            setTaskId(args.taskId)
            setProjectEndDate(args.projectEndDate)
            setProjectStartDate(args.projectStartDate)
        }
        observeTaskId(viewModel)
        observeTaskDetails(viewModel)
        observeEstimateEndDate(viewModel)
        observeEstimateStartDate(viewModel)
        setListeners(viewModel)
        observeDeleteTaskState(viewModel)
        observeEditTaskState(viewModel)
    }

    private fun setListeners(viewModel: TaskDetailsViewModel) {
        with(binding) {
            editTaskButton.setOnClickListener {
                listOf(titleEditText, descriptionEditText).forEach { view ->
                    view.enableOrDisableView()
                }
                submitButton.changeVisibility()
                listOf(startDateChangeButton, endDateChangeButton).forEach { view ->
                    view.changeVisibilityByParam(submitButton.isVisible)
                }
                listOf(titleEditText, descriptionEditText).forEach { view ->
                    view.changeSameViewBackground(
                        submitButton.isVisible,
                        R.drawable.enabled_edit_text_background,
                        R.drawable.edit_text_background
                    )
                }
                statusChipGroup.setChipsEnabledOrDisabled(submitButton.isVisible)
            }
            startDateChangeButton.setOnClickListener {
                setDatePicker(
                    minDate = viewModel.projectStartDate.value.getDateByTime().time,
                    maxDate = viewModel.projectEndDate.value.getDateByTime().time
                ) {
                    viewModel.setEstimateStartDate(it)
                }
            }
            endDateChangeButton.setOnClickListener {
                setDatePicker(
                    minDate = viewModel.startDate.value.getDateByTime().time,
                    maxDate = viewModel.projectEndDate.value.getDateByTime().time
                ) { viewModel.setEstimateEndDate(it) }
            }
            submitButton.setOnClickListener {
                editTask(viewModel)
            }
            deleteTaskButton.setOnClickListener {
                viewModel.taskId.value?.let { id -> viewModel.deleteTask(id) }
            }
        }
    }

    private fun editTask(viewModel: TaskDetailsViewModel) {
        with(binding) {
            with(viewModel) {
                val editedTask = Task(
                    taskTitle = titleEditText.text.toString(),
                    taskDescription = descriptionEditText.text.toString(),
                    status = status.value!!,
                    startTime = startDate.value!!,
                    endTime = endDate.value!!
                )
                setEditTask(editedTask)
            }
        }
    }

    private fun observeEditTaskState(viewModel: TaskDetailsViewModel) {
        flowObserver(viewModel.editTaskState) { state ->
            if (state.success != null) {
                TaskDetailsFragmentDirections.actionTaskDetailsFragmentToProjectDetailFragment(
                    viewModel.projectId.value
                ).also { findNavController().navigate(it) }
                setSnackBar(getString(string.successfully_edited_task), Color.GREEN)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText, Color.RED)
            }
        }
    }

    private fun observeDeleteTaskState(viewModel: TaskDetailsViewModel) {
        flowObserver(viewModel.deleteTaskState) { state ->
            if (state.success != null) {
                TaskDetailsFragmentDirections.actionTaskDetailsFragmentToProjectDetailFragment(
                    viewModel.projectId.value
                ).also { findNavController().navigate(it) }
                setSnackBar(getString(string.successfully_deleted_task), Color.GREEN)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText, Color.RED)
            }
        }
    }

    private fun observeTaskDetails(viewModel: TaskDetailsViewModel) {
        flowObserver(viewModel.taskDetailScreenState) { state ->
            binding.loadingProgressBar.isVisible = state.isLoading
            if (state.success != null) {
                setTaskDetails(viewModel, state.success)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText, Color.RED)
            }
        }
    }

    private fun observeTaskId(viewModel: TaskDetailsViewModel) {
        flowObserver(viewModel.taskId) { taskId ->
            taskId?.let {
                viewModel.setTaskDetails(it)
            }
        }
    }

    private fun setTaskDetails(viewModel: TaskDetailsViewModel, task: Task) {
        with(binding) {
            with(task) {
                titleEditText.setText(taskTitle)
                descriptionEditText.setText(taskDescription)
                setChipStatus(status.getStatusByOrdinal())
                with(viewModel) {
                    setEstimateStartDate(startTime!!)
                    setEstimateEndDate(endTime!!)
                    setProjectId(task.projectId!!)
                }
            }
            statusChipGroup.setChipsDisabled()
        }
        setFabMotionAnimation()
    }

    private fun observeEstimateStartDate(viewModel: TaskDetailsViewModel) {
        liveDataObserver(viewModel.startDate) {
            binding.startDateTextView.text = getString(string.txt_estimate_start_date, it)
        }
    }

    private fun observeEstimateEndDate(viewModel: TaskDetailsViewModel) {
        liveDataObserver(viewModel.endDate) {
            binding.endDateTextView.text = getString(string.txt_estimate_end_date, it)
        }
    }

    private fun setChipStatus(status: Status) {
        with(binding) {
            when (status) {
                Status.TODO -> {
                    todoStateChip.isChecked = true
                }
                Status.DONE -> {
                    doneStateChip.isChecked = true
                }
                Status.IN_PROGRESS -> {
                    inProgressStateChip.isChecked = true
                }
            }
        }
    }

    private fun setSnackBar(message: String, color: Int) {
        createSnackBar(message) {
            snackAction(color, getString(string.ok)) {
                dismiss()
            }
        }
    }

    private fun setFabMotionAnimation() {
        with(binding) {
            motionLayout.setActionOnSpecifiedProgress(
                TRANSITION_DEF_PROGRESS,
                {
                    moreOptionButton.setDrawableImage(requireContext(), R.drawable.ic_more)
                },
                {
                    moreOptionButton.setDrawableImage(requireContext(), R.drawable.ic_close)
                })
        }
    }
}