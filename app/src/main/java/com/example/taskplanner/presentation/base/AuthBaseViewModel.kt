package com.example.taskplanner.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.extension.isValidEmail
import com.example.taskplanner.presentation.authorization.registration_screen.AuthScreenState
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthBaseViewModel @Inject constructor(
    protected val resourcesProvider: ResourcesProvider
) : ViewModel() {
    protected val _screenState = MutableStateFlow(AuthScreenState())
    val screenState: StateFlow<AuthScreenState> = _screenState

    protected fun validateIfIsEmpty(input: String): Boolean {
        return input.trim().isBlank()
    }

    protected fun validateEmail(email: String): Boolean {
        return !validateIfIsEmpty(email) && email.isValidEmail()
    }

    protected fun handleResponse(response: Resource<AuthResult>) = viewModelScope.launch {
        when (response) {
            is Resource.Success -> {
                _screenState.emit(AuthScreenState(isSuccess = true))
            }
            is Resource.Error -> {
                _screenState.emit(AuthScreenState(errorText = response.errorMessage))
            }
        }
    }
}