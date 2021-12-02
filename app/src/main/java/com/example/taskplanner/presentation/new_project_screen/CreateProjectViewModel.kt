package com.example.taskplanner.presentation.new_project_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.project.ProjectRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.authorization.registration_screen.ScreenState
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    @ApplicationContext appCtx: Context
) : BaseViewModel(ResourcesProvider(appCtx)) {

    private val _startDate = MutableLiveData<String?>()
    val startDate: LiveData<String?> = _startDate

    private val _endDate = MutableLiveData<String?>()
    val endDate: LiveData<String?> = _endDate

    fun setEstimateStartDate(date: String) = viewModelScope.launch {
        _startDate.postValue(date)
    }

    fun setEstimateEndDate(date: String) = viewModelScope.launch {
        _endDate.postValue(date)
    }

    fun setProject(
        projectTitle: String,
        projectDescription: String,
        startDate: String?,
        endDate: String?
    ) = viewModelScope.launch {
        when {
            checkIfIsNull(startDate) || checkIfIsNull(endDate) -> {
                _screenState.emit(ScreenState(errorText = resourcesProvider.getString(string.please_choose_estimate_time)))
            }
            checkIfIsEmpty(projectTitle) || checkIfIsEmpty(projectDescription) -> {
                _screenState.emit(ScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields)))
            }
            else -> {
                handleResponse(
                    projectRepository.setProject(
                        projectTitle,
                        projectDescription,
                        startDate!!,
                        endDate!!
                    )
                )
            }
        }
    }
}