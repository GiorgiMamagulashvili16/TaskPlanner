package com.example.taskplanner.presentation.project_screen.project_detail

import com.example.taskplanner.databinding.ProjectDetailFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate

class ProjectDetailFragment : BaseFragment<ProjectDetailFragmentBinding,ProjectDetailViewModel>() {
    override fun inflateFragment(): Inflate<ProjectDetailFragmentBinding> {
        return ProjectDetailFragmentBinding::inflate
    }

    override fun getVmClass(): Class<ProjectDetailViewModel> {
        return ProjectDetailViewModel::class.java
    }

    override fun onBindViewModel(viewModel: ProjectDetailViewModel) {
        TODO("Not yet implemented")
    }

}