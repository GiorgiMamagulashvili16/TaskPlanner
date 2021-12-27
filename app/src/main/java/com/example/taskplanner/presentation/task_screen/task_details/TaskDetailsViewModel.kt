package com.example.taskplanner.presentation.task_screen.task_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.repository.task.TaskRepository
import com.example.taskplanner.data.util.ValidatorHelper
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
    private val taskRepository: TaskRepository,
    validatorHelper: ValidatorHelper
) : ProjectBaseViewModel(appCtx, validatorHelper) {

    private val _taskId = MutableStateFlow<String?>(null)
    val taskId: StateFlow<String?> = _taskId

    private val _taskDetailsScreenState = MutableStateFlow(ScreenState<Task>())
    val taskDetailScreenState: StateFlow<ScreenState<Task>> = _taskDetailsScreenState

    private val _deleteTaskState = MutableStateFlow(ScreenState<Unit>())
    val deleteTaskState: StateFlow<ScreenState<Unit>> = _deleteTaskState

    private val _editTaskState = MutableStateFlow(ScreenState<Unit>())
    val editTaskState: StateFlow<ScreenState<Unit>> = _editTaskState

    private val _projectStartDate = MutableLiveData<Long>()
    val projectStartDate: LiveData<Long> = _projectStartDate

    private val _projectEndDate = MutableLiveData<Long>()
    val projectEndDate: LiveData<Long> = _projectEndDate

    fun setProjectStartDate(newStartDate: Long) = viewModelScope.launch {
        _projectStartDate.postValue(newStartDate)
    }

    fun setProjectEndDate(newEndDate: Long) = viewModelScope.launch {
        _projectEndDate.postValue(newEndDate)
    }

    fun setEditTask(title: String, description: String) = viewModelScope.launch {
        if (validatorHelper.checkParamsIsNotBlank(listOf(title, description)) {
                emitFlowErrorState(
                    _editTaskState,
                    it
                )
            } && validatorHelper.checkDatesIsNotNull(
                listOf(
                    startDate.value,
                    endDate.value
                )
            ) { emitFlowErrorState(_editTaskState, it) }
        ) {
            val editedTask = Task(
                taskTitle = title,
                taskDescription = description,
                status = status.value!!,
                startTime = startDate.value!!,
                endTime = endDate.value!!,
                taskId = taskId.value
            )
            handleResponse(taskRepository.editTask(editedTask), _editTaskState)
        }
    }

    fun deleteTask(taskId: String) = viewModelScope.launch {
        handleResponse(taskRepository.deleteTask(taskId), _deleteTaskState)
    }

    fun setTaskDetails(taskId: String) = viewModelScope.launch {
        _taskDetailsScreenState.emit(ScreenState(isLoading = true))
        handleResponse(taskRepository.getTaskByTaskId(taskId), _taskDetailsScreenState)
    }

    fun setTaskId(newTaskId: String) = viewModelScope.launch {
        _taskId.emit(newTaskId)
    }
}