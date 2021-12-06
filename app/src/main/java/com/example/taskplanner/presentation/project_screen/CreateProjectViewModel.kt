package com.example.taskplanner.presentation.project_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.presentation.authorization.registration_screen.string
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
    @ApplicationContext appCtx: Context
) : ProjectBaseViewModel(appCtx) {

    private val _createProjectScreenState = MutableStateFlow(ScreenState<Unit>())
    val createScreenState: StateFlow<ScreenState<Unit>> = _createProjectScreenState

    fun setProject(project: Project) = viewModelScope.launch {
        with(project) {
            when {
                checkIfIsNull(startDate) || checkIfIsNull(endDate) -> {
                    _createProjectScreenState.emit(
                        ScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_choose_estimate_time
                            )
                        )
                    )
                }
                checkIfIsEmpty(projectTitle!!) || checkIfIsEmpty(projectDescription!!) -> {
                    _createProjectScreenState.emit(
                        ScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_fill_all_fields
                            )
                        )
                    )
                }
                else -> {
                    handleResponse(
                        projectRepository.setProject(project),
                        _createProjectScreenState
                    )
                }
            }
        }

    }
}