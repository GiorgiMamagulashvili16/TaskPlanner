package com.example.taskplanner.presentation.project_screen

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
class CreateProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    @ApplicationContext appCtx: Context, validatorHelper: ValidatorHelper
) : ProjectBaseViewModel(appCtx, validatorHelper) {

    private val _createProjectScreenState = MutableStateFlow(ScreenState<Unit>())
    val createProjectScreenState: StateFlow<ScreenState<Unit>> = _createProjectScreenState

    fun setProject(title: String, description: String) = viewModelScope.launch {
        _createProjectScreenState.emit(ScreenState(isLoading = true))
        if (validatorHelper.checkParamsIsBlank(listOf(title, description)) {
                emitFlowErrorState(
                    _createProjectScreenState,
                    it
                )
            } && validatorHelper.checkParamsIsNull(
                listOf(
                    startDate.value,
                    endDate.value
                )
            ) { emitFlowErrorState(_createProjectScreenState, it) }
        ) {
            val project = Project(
                projectTitle = title,
                projectDescription = description,
                startDate = startDate.value,
                endDate = endDate.value
            )
            handleResponse(projectRepository.setProject(project), _createProjectScreenState)
        }
    }
}