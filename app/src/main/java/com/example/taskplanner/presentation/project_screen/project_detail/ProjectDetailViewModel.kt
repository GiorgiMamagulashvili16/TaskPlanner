package com.example.taskplanner.presentation.project_screen.project_detail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.repository.project.ProjectRepository
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
class ProjectDetailViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val projectRepository: ProjectRepository,
    validatorHelper: ValidatorHelper
) : ProjectBaseViewModel(appCtx, validatorHelper) {

    private val _projectDetailScreenState = MutableStateFlow(ScreenState<Project>())
    val projectDetailScreenState: StateFlow<ScreenState<Project>> = _projectDetailScreenState

    private val _deleteProjectState = MutableStateFlow(ScreenState<Unit>())
    val deleteProjectState: StateFlow<ScreenState<Unit>> = _deleteProjectState

    private val _editProjectDetailsState = MutableStateFlow(ScreenState<Unit>())
    val editProjectDetailState: StateFlow<ScreenState<Unit>> = _editProjectDetailsState

    fun editProjectDetailInfo(title: String, description: String) = viewModelScope.launch {
        _editProjectDetailsState.emit(ScreenState(isLoading = true))
        if (validatorHelper.checkParamsIsNotBlank(listOf(title, description)) {
                emitFlowErrorState(
                    _editProjectDetailsState,
                    it
                )
            } && validatorHelper.checkDatesIsNotNull(listOf(startDate.value, endDate.value)) {
                emitFlowErrorState(_editProjectDetailsState, it)
            }
        ) {
            val project = Project(
                projectId = projectId.value!!,
                projectTitle = title,
                projectDescription = description,
                projectStatus = status.value!!,
                startDate = startDate.value,
                endDate = endDate.value
            )
            handleResponse(projectRepository.editProjectInfo(project), _editProjectDetailsState)
        }
    }

    fun setProjectDetailData(projectId: String) = viewModelScope.launch {
        _projectDetailScreenState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.getProjectById(projectId), _projectDetailScreenState)
    }

    fun deleteProject(projectId: String) = viewModelScope.launch {
        _deleteProjectState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.deleteProjectById(projectId), _deleteProjectState)
    }
}