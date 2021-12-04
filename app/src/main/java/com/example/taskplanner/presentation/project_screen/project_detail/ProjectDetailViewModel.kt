package com.example.taskplanner.presentation.project_screen.project_detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.base.BaseViewModel
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
    private val projectRepository: ProjectRepository
) : BaseViewModel(ResourcesProvider(appCtx)) {

    private val _projectId = MutableLiveData<String?>()
    val projectId: LiveData<String?> = _projectId

    private val _projectDetailScreenState = MutableStateFlow(ScreenState<Project>())
    val projectDetailScreenState: StateFlow<ScreenState<Project>> = _projectDetailScreenState

    private val _deleteProjectState = MutableStateFlow(ScreenState<Unit>())
    val deleteProjectState: StateFlow<ScreenState<Unit>> = _deleteProjectState

    fun setProjectDetailData(projectId: String) = viewModelScope.launch {
        _projectDetailScreenState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.getProjectById(projectId), _projectDetailScreenState)
    }

    fun deleteProject(projectId: String) = viewModelScope.launch {
        _deleteProjectState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.deleteProjectById(projectId), _deleteProjectState)
    }

    fun setProjectId(projectId: String) = viewModelScope.launch {
        _projectId.postValue(projectId)
    }
}