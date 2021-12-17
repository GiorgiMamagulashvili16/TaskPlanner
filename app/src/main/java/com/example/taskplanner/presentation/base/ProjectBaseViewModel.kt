package com.example.taskplanner.presentation.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.ValidatorHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProjectBaseViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    validatorHelper: ValidatorHelper
) : BaseViewModel(
    ResourcesProvider(appCtx), validatorHelper
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
}