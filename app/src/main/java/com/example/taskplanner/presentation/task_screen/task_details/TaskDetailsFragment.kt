package com.example.taskplanner.presentation.task_screen.task_details

import com.example.taskplanner.databinding.TaskDetailsFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : BaseFragment<TaskDetailsFragmentBinding,TaskDetailsViewModel>() {
    override fun inflateFragment(): Inflate<TaskDetailsFragmentBinding> {
        return TaskDetailsFragmentBinding::inflate
    }

    override fun getVmClass(): Class<TaskDetailsViewModel> {
        return TaskDetailsViewModel::class.java
    }

    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {

    }
    private fun setListeners(viewModel: TaskDetailsViewModel){
        with(binding){

        }
    }
}