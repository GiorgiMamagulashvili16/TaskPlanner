package com.example.taskplanner.presentation.project_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Project
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
class CreateProjectViewModel @Inject constructor(
    projectRepository: ProjectRepository,
    @ApplicationContext appCtx: Context,
    taskRepository: TaskRepository
) : ProjectBaseViewModel(appCtx, projectRepository, taskRepository) {

    private val _createProjectScreenState = MutableStateFlow(ScreenState<Unit>())
    val createProjectScreenState: StateFlow<ScreenState<Unit>> = _createProjectScreenState

    fun setProject(project: Project) = viewModelScope.launch {
        _createProjectScreenState.emit(ScreenState(isLoading = true))
        addNewProject(
            _createProjectScreenState,
            project
        )
    }
}