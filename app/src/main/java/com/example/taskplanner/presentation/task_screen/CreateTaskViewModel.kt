package com.example.taskplanner.presentation.task_screen

import android.content.Context
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
class CreateTaskViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val taskRepository: TaskRepository, validatorHelper: ValidatorHelper
) : ProjectBaseViewModel(appCtx, validatorHelper) {

    val projectStartDate = MutableLiveData<String>()

    val projectEndDate = MutableLiveData<String>()

    private val _createTaskScreenState = MutableStateFlow(ScreenState<Unit>())
    val createTaskScreenState: StateFlow<ScreenState<Unit>> = _createTaskScreenState

    fun setProjectStartDate(newStartDate: String) = viewModelScope.launch {
        projectStartDate.postValue(newStartDate)
    }

    fun setProjectEndDate(newEndDate: String) = viewModelScope.launch {
        projectEndDate.postValue(newEndDate)
    }

    fun setTask(title: String, description: String) = viewModelScope.launch {
        _createTaskScreenState.emit(ScreenState(isLoading = true))
        if (validatorHelper.checkParamsIsBlank(listOf(title, description)) {
                emitFlowErrorState(
                    _createTaskScreenState,
                    it
                )
            } && validatorHelper.checkParamsIsNull(
                listOf(
                    startDate.value,
                    endDate.value,
                )
            ) { emitFlowErrorState(_createTaskScreenState, it) }
        ) {
            val task = Task(
                projectId = projectId.value,
                taskTitle = title,
                taskDescription = description,
                startTime = startDate.value,
                endTime = endDate.value,
            )
            handleResponse(taskRepository.setTask(task), _createTaskScreenState)
        }
    }
}