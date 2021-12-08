package com.example.taskplanner.presentation.project_screen.project_detail

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.util.extension.*
import com.example.taskplanner.databinding.ProjectDetailFragmentBinding
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.example.taskplanner.presentation.project_screen.ProjectStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectDetailFragment : BaseFragment<ProjectDetailFragmentBinding, ProjectDetailViewModel>() {
    override fun inflateFragment(): Inflate<ProjectDetailFragmentBinding> {
        return ProjectDetailFragmentBinding::inflate
    }

    override fun getVmClass(): Class<ProjectDetailViewModel> {
        return ProjectDetailViewModel::class.java
    }

    private val args: ProjectDetailFragmentArgs by navArgs()
    override fun onBindViewModel(viewModel: ProjectDetailViewModel) {
        viewModel.setProjectId(args.projectId)
        observeProjectId(viewModel)
        observeScreenState(viewModel)
        setListeners(viewModel)
        observeProjectDeleteState(viewModel)
        setChipStatus(viewModel)
        observeEditProjectDetailsResponseState(viewModel)
        observeStartDate(viewModel)
        observeEndDate(viewModel)
    }

    private fun setListeners(viewModel: ProjectDetailViewModel) {
        with(binding) {
            editProjectButton.setOnClickListener {
                listOf(titleEditText, descriptionEditText).forEach { view ->
                    view.enableOrDisableView()
                }
                listOf(submitButton, startDateChangeButton, endDateChangeButton).forEach { view ->
                    view.changeVisibility()
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
            deleteProjectButton.setOnClickListener {
                viewModel.projectId.value?.let { id -> viewModel.deleteProject(id) }
            }
            startDateChangeButton.setOnClickListener {
                setDatePicker {
                    viewModel.setEstimateStartDate(it)
                }
            }
            endDateChangeButton.setOnClickListener {
                setDatePicker {
                    viewModel.setEstimateEndDate(it)
                }
            }
            submitButton.setOnClickListener {
                updateProjectData(viewModel)
            }
        }
    }

    private fun observeProjectId(viewModel: ProjectDetailViewModel) {
        liveDataObserver(viewModel.projectId) { projectId ->
            if (projectId != null) {
                viewModel.setProjectDetailData(projectId)
            }
        }
    }

    private fun observeScreenState(viewModel: ProjectDetailViewModel) {
        flowObserver(viewModel.projectDetailScreenState) { state ->
            if (state.success != null) {
                setDetailData(state.success, viewModel)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText)
            }
        }
        setFabMotionAnimation()
    }

    private fun observeProjectDeleteState(viewModel: ProjectDetailViewModel) {
        flowObserver(viewModel.deleteProjectState) { state ->
            if (state.success != null) {
                findNavController().navigate(R.id.action_projectDetailFragment_to_homeFragment)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText)
            }
        }
    }

    private fun observeStartDate(viewModel: ProjectDetailViewModel) {
        liveDataObserver(viewModel.startDate) {
            binding.startDateTextView.text = getString(string.txt_estimate_start_date, it)
        }
    }

    private fun observeEndDate(viewModel: ProjectDetailViewModel) {
        liveDataObserver(viewModel.endDate) {
            binding.endDateTextView.text = getString(string.txt_estimate_end_date, it)
        }
    }

    private fun observeEditProjectDetailsResponseState(viewModel: ProjectDetailViewModel) {
        flowObserver(viewModel.editProjectDetailState) { state ->
            if (state.success != null) {
                setSnackBar(getString(string.successfully_edited_project))
                findNavController().navigate(R.id.action_projectDetailFragment_to_homeFragment)
            } else if (state.errorText != null) {
                setSnackBar(state.errorText)
            }
        }
    }

    private fun updateProjectData(viewModel: ProjectDetailViewModel) {
        with(binding) {
            with(viewModel) {
                val project = Project(
                    projectId = projectId.value!!,
                    projectTitle = titleEditText.text.toString(),
                    projectDescription = descriptionEditText.text.toString(),
                    startDate = startDate.value,
                    endDate = endDate.value,
                    projectStatus = projectStatus.value!!
                )
                editProjectDetailInfo(project)
            }
        }
    }

    private fun setDetailData(project: Project, viewModel: ProjectDetailViewModel) {
        with(binding) {
            with(project) {
                titleEditText.setText(projectTitle)
                descriptionEditText.setText(projectDescription)
                startDateTextView.text = getString(string.txt_estimate_start_date, startDate)
                endDateTextView.text = getString(string.txt_estimate_end_date, endDate)

                ProjectStatus.values().forEach { status ->
                    if (projectStatus == status.title) {
                        when (status) {
                            ProjectStatus.TODO -> {
                                todoStateChip.isChecked = true
                            }
                            ProjectStatus.DONE -> {
                                doneStateChip.isChecked = true
                            }
                            ProjectStatus.IN_PROGRESS -> {
                                inProgressStateChip.isChecked = true
                            }
                        }

                    }
                }
            }
            with(viewModel) {
                setEstimateStartDate(project.startDate!!)
                setEstimateEndDate(project.endDate!!)
                setProjectStatus(project.projectStatus)
            }
            statusChipGroup.setChipsDisabled()
        }
    }

    private fun setChipStatus(viewModel: ProjectDetailViewModel) {
        binding.statusChipGroup.setOnCheckedChangeListener { _, checkedId ->
            with(viewModel) {
                when (checkedId) {
                    R.id.todoStateChip -> {
                        setProjectStatus(ProjectStatus.TODO.title)
                    }
                    R.id.inProgressStateChip -> {
                        setProjectStatus(ProjectStatus.IN_PROGRESS.title)
                    }
                    R.id.doneStateChip -> {
                        setProjectStatus(ProjectStatus.DONE.title)
                    }
                }
            }
        }
    }

    private fun setSnackBar(message: String) {
        createSnackBar(message) {
            snackAction(Color.GREEN, getString(string.ok)) {
                dismiss()
            }
        }
    }

    private fun setFabMotionAnimation() {
        with(binding) {
            root.setActionOnSpecifiedProgress(TRANSITION_DEF_PROGRESS,
                {
                    moreOptionButton.setDrawableImage(requireContext(), R.drawable.ic_more)
                },
                {
                    moreOptionButton.setDrawableImage(requireContext(), R.drawable.ic_close)
                })
        }
    }

    companion object {
        private const val TRANSITION_DEF_PROGRESS = 0.4f
    }
}