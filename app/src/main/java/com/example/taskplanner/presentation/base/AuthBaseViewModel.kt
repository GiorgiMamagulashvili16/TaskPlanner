package com.example.taskplanner.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.extension.isValidEmail
import com.example.taskplanner.presentation.authorization.registration_screen.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthBaseViewModel @Inject constructor(
    protected val resourcesProvider: ResourcesProvider
) : ViewModel() {
    protected val _screenState = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> = _screenState

    protected fun checkIfIsEmpty(input: String): Boolean {
        return input.trim().isBlank()
    }

    protected fun validateEmail(email: String): Boolean {
        return !checkIfIsEmpty(email) && email.isValidEmail()
    }

    protected fun checkIfIsNull(input: String?): Boolean {
        return input == null
    }

    protected fun <T> handleResponse(response: Resource<T>) = viewModelScope.launch {
        when (response) {
            is Resource.Success -> {
                _screenState.emit(ScreenState(isSuccess = true))
            }
            is Resource.Error -> {
                _screenState.emit(ScreenState(errorText = response.errorMessage))
            }
        }
    }
}