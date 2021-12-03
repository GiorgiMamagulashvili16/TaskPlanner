package com.example.taskplanner.presentation.home_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    @ApplicationContext appCtx: Context
) : ViewModel() {
    private val _screenState = MutableStateFlow(ProjectScreenState<User>())
    val screenState: StateFlow<ProjectScreenState<User>> = _screenState
    fun getCurrentUserData() = viewModelScope.launch {
        _screenState.emit(ProjectScreenState(isLoading = true))
        handleResponse(projectRepository.getCurrentUserData())
    }

    private fun handleResponse(response: Resource<User>) = viewModelScope.launch {
        when (response) {
            is Resource.Success -> {
                _screenState.emit(ProjectScreenState(successData = response.data))
            }
            is Resource.Error -> {
                _screenState.emit(ProjectScreenState(errorText = response.errorMessage))
            }
        }
    }
}