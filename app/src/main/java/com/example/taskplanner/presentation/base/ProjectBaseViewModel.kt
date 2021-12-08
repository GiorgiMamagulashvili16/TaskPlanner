package com.example.taskplanner.presentation.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProjectBaseViewModel @Inject constructor(@ApplicationContext appCtx: Context) :
    BaseViewModel(
        ResourcesProvider(appCtx)
    ) {
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

}