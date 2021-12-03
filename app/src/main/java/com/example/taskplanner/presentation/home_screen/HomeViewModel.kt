package com.example.taskplanner.presentation.home_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.screen_state.ScreenState
import com.example.taskplanner.presentation.base.BaseViewModel
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
) : BaseViewModel(ResourcesProvider(appCtx)) {
    private val _homeScreenState = MutableStateFlow(ScreenState<User>())
    val homeScreenState: StateFlow<ScreenState<User>> = _homeScreenState

    fun getCurrentUserData() = viewModelScope.launch {
        _homeScreenState.emit(ScreenState(isLoading = true))
        handleResponse(projectRepository.getCurrentUserData(), _homeScreenState)
    }
}