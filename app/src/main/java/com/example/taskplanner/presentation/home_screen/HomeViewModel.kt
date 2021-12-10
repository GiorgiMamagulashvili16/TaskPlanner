package com.example.taskplanner.presentation.home_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.auth.AuthRepository
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.repository.task.TaskRepository
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
class HomeViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    @ApplicationContext appCtx: Context,
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : BaseViewModel(ResourcesProvider(appCtx)) {
    private val _homeScreenState = MutableStateFlow(ScreenState<User>())
    val homeScreenState: StateFlow<ScreenState<User>> = _homeScreenState

    private val _todoTasksNumber = MutableStateFlow(DEF_TASK_NUMBER)
    val todoTaskNumber: StateFlow<Int> = _todoTasksNumber

    private val _inProgressTasksNumber = MutableStateFlow(DEF_TASK_NUMBER)
    val inProgressTaskNumber: StateFlow<Int> = _inProgressTasksNumber

    private val _doneTasksNumber = MutableStateFlow(DEF_TASK_NUMBER)
    val doneTaskNumber: StateFlow<Int> = _doneTasksNumber

    private val _logOut = MutableStateFlow<Unit?>(null)
    val logOut: StateFlow<Unit?> = _logOut

    fun logOut() = viewModelScope.launch {
        _logOut.emit(authRepository.signOut().data)
    }

    fun setTaskNumbers() = viewModelScope.launch {
        _todoTasksNumber.emit(taskRepository.getAllTodoTasksNumber().data!!)
        _inProgressTasksNumber.emit(taskRepository.getAllInProgressTaskNumber().data!!)
        _doneTasksNumber.emit(taskRepository.getAllDoneTaskNumber().data!!)
    }

    fun getCurrentUserData() = viewModelScope.launch {
        _homeScreenState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.getCurrentUserData(), _homeScreenState)
    }

    companion object {
        private const val DEF_TASK_NUMBER = 0
    }
}