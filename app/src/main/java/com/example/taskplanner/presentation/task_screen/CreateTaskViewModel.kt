package com.example.taskplanner.presentation.task_screen

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
class CreateTaskViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val taskRepository: TaskRepository,
    projectRepository: ProjectRepository
) : ProjectBaseViewModel(appCtx, projectRepository, taskRepository) {

    private val _projectStartDate = MutableLiveData<String>()
    val projectStartDate: LiveData<String> = _projectStartDate

    private val _projectEndDate = MutableLiveData<String>()
    val projectEndDate: LiveData<String> = _projectEndDate

    private val _createTaskScreenState = MutableStateFlow(ScreenState<Unit>())
    val createTaskScreenState: StateFlow<ScreenState<Unit>> = _createTaskScreenState

    fun setProjectStartDate(newStartDate: String) = viewModelScope.launch {
        _projectStartDate.postValue(newStartDate)
    }

    fun setProjectEndDate(newEndDate: String) = viewModelScope.launch {
        _projectEndDate.postValue(newEndDate)
    }

    fun setTask(task: Task) = viewModelScope.launch {
        setNewItem(_createTaskScreenState, task, projectStartDate.value, projectEndDate.value)
    }
}