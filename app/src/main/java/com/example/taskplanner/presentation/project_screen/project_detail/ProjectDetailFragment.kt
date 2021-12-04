package com.example.taskplanner.presentation.project_screen.project_detail

import android.graphics.Color
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
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
    }

    private fun observeProjectId(viewModel: ProjectDetailViewModel) {
        observeData(viewModel.projectId) { projectId ->
            if (projectId != null) {
                viewModel.setProjectDetailData(projectId)
            }
        }
    }

    private fun observeScreenState(viewModel: ProjectDetailViewModel) {
        flowObserver(viewModel.projectDetailScreenState) { state ->
            if (state.success != null) {
                setDetailData(state.success)
            } else if (state.errorText != null) {
                createSnackBar(state.errorText) {
                    snackAction(Color.RED, getString(string.ok)) {
                        dismiss()
                    }
                }
            }
        }
        setFabMotionAnimation()
    }

    private fun observeProjectDeleteState(viewModel: ProjectDetailViewModel) {
        flowObserver(viewModel.deleteProjectState) { state ->
            if (state.success != null) {
                findNavController().navigate(R.id.action_projectDetailFragment_to_homeFragment)
            } else if (state.errorText != null) {
                createSnackBar(state.errorText) {
                    snackAction(Color.RED, getString(string.ok)) {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun setFabMotionAnimation() {
        with(binding) {
            root.setActionOnSpecifiedProgress(0.4f,
                {
                    moreOptionButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_more
                        )
                    )
                },
                {
                    moreOptionButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_close
                        )
                    )
                })
        }
    }

    private fun setListeners(viewModel: ProjectDetailViewModel) {
        with(binding) {
            editProjectButton.setOnClickListener {
                enableOrDisableViews(titleEditText, descriptionEditText)
                viewVisibilityChanger(submitButton)
                changeViewBackground(
                    titleEditText,
                    descriptionEditText,
                    isEnabled = submitButton.isVisible,
                    enabledBackground = R.drawable.enabled_edit_text_background,
                    defaultBackground = R.drawable.edit_text_background
                )
            }
            deleteProjectButton.setOnClickListener {
                viewModel.projectId.value?.let { id -> viewModel.deleteProject(id) }
            }
        }
    }

    private fun enableOrDisableViews(vararg views: View) {
        views.forEach {
            it.isEnabled = !it.isEnabled
        }
    }

    private fun viewVisibilityChanger(vararg view: View) {
        view.forEach {
            it.isVisible = !it.isVisible
        }
    }

    private fun changeViewBackground(
        vararg views: View,
        isEnabled: Boolean,
        enabledBackground: Int,
        defaultBackground: Int
    ) {
        views.forEach {
            if (isEnabled) {
                it.setBackgroundResource(enabledBackground)
            } else {
                it.setBackgroundResource(defaultBackground)
            }
        }
    }

    private fun setDetailData(project: Project) {
        with(binding) {
            with(project) {
                titleEditText.setText(projectTitle)
                descriptionEditText.setText(projectDescription)
                startDateTextView.text = getString(string.txt_estimate_start_date, startDate)
                endDateTextView.text = getString(string.txt_estimate_end_date, endDate)
            }
        }
    }
}