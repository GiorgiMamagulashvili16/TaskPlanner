package com.example.taskplanner.presentation.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.repository.task.TaskRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProjectBaseViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) :
    BaseViewModel(
        ResourcesProvider(appCtx)
    ) {
    private val _startDate = MutableLiveData<String?>()
    val startDate: LiveData<String?> = _startDate

    private val _endDate = MutableLiveData<String?>()
    val endDate: LiveData<String?> = _endDate

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> = _status


    private val _projectId = MutableLiveData<String?>()
    val projectId: LiveData<String?> = _projectId


    fun setProjectId(projectId: String) = viewModelScope.launch {
        _projectId.postValue(projectId)
    }

    fun setStatus(newStatus: Int) = viewModelScope.launch {
        _status.postValue(newStatus)
    }

    fun setEstimateStartDate(date: String) = viewModelScope.launch {
        _startDate.postValue(date)
    }

    fun setEstimateEndDate(date: String) = viewModelScope.launch {
        _endDate.postValue(date)
    }

    fun <T> setNewItem(
        flow: MutableStateFlow<ScreenState<Unit>>,
        item: T,
    ) = viewModelScope.launch {
        when (item) {
            is Project -> {
                with(item) {
                    if (checkItemParams(
                            listOf(projectTitle!!, projectDescription!!),
                            flow
                        ) && checkCurrentItemEstimateDates(flow)
                    ) {
                        handleResponse(projectRepository.setProject(item), flow)
                    }
                }
            }
            is Task -> {
                with(item) {
                    if (checkItemParams(
                            listOf(taskTitle!!, taskDescription!!), flow
                        ) && checkCurrentItemEstimateDates(flow)
                    ) {
                        handleResponse(taskRepository.setTask(item), flow)
                    }
                }
            }
        }
    }

    private fun checkItemParams(
        itemParams: List<String>,
        flow: MutableStateFlow<ScreenState<Unit>>
    ): Boolean {
        val result = itemParams.filter { it.isBlank() }
        if (result.isNotEmpty()) {
            flow.value = ScreenState(
                errorText = resourcesProvider.getString(
                    string.please_fill_all_fields
                )
            )
        }
        return result.isEmpty()
    }

    private fun checkCurrentItemEstimateDates(
        flow: MutableStateFlow<ScreenState<Unit>>,
    ): Boolean {
        return if (startDate.value == null || endDate.value == null) {
            flow.value = ScreenState(
                errorText = resourcesProvider.getString(
                    string.please_choose_estimate_time
                )
            )
            false
        } else {
            true
        }
    }
}