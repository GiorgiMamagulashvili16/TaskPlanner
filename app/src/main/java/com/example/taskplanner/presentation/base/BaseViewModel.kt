package com.example.taskplanner.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.ValidatorHelper
import com.example.taskplanner.presentation.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    val resourcesProvider: ResourcesProvider,
    val validatorHelper: ValidatorHelper
) : ViewModel() {

    protected fun <T> handleResponse(
        response: Resource<T>,
        flow: MutableStateFlow<ScreenState<T>>
    ) = viewModelScope.launch {
        when (response) {
            is Resource.Success -> {
                flow.emit(ScreenState(success = response.data))
            }
            is Resource.Error -> {
                flow.emit(ScreenState(errorText = response.errorMessage))
            }
        }
    }

    protected fun <T> emitFlowErrorState(flow: MutableStateFlow<ScreenState<T>>, message: Int) =
        viewModelScope.launch {
            flow.emit(ScreenState(errorText = resourcesProvider.getString(message)))
        }

}