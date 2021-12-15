package com.example.taskplanner.presentation.task_screen.task_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.repository.task.TaskRepository
import com.example.taskplanner.presentation.base.ProjectBaseViewModel
import com.example.taskplanner.presentation.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) : ProjectBaseViewModel(appCtx, projectRepository, taskRepository) {

    private val _taskId = MutableStateFlow<String?>(null)
    val taskId: StateFlow<String?> = _taskId

    private val _taskDetailsScreenState = MutableStateFlow(ScreenState<Task>())
    val taskDetailScreenState: StateFlow<ScreenState<Task>> = _taskDetailsScreenState


    private val _projectStartDate = MutableLiveData<String>()
    val projectStartDate: LiveData<String> = _projectStartDate

    private val _projectEndDate = MutableLiveData<String>()
    val projectEndDate: LiveData<String> = _projectEndDate

    fun setProjectStartDate(newStartDate: String) = viewModelScope.launch {
        _projectStartDate.postValue(newStartDate)
    }

    fun setProjectEndDate(newEndDate: String) = viewModelScope.launch {
        _projectEndDate.postValue(newEndDate)
    }

    fun setTaskDetails(taskId: String) = viewModelScope.launch {
        _taskDetailsScreenState.emit(ScreenState(isLoading = true))
        handleResponse(taskRepository.getTaskByTaskId(taskId), _taskDetailsScreenState)
    }

    fun setTaskId(newTaskId: String) = viewModelScope.launch {
        _taskId.emit(newTaskId)
    }
}