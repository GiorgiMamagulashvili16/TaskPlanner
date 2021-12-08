package com.example.taskplanner.presentation.authorization.login_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.AuthBaseViewModel
import com.example.taskplanner.presentation.screen_state.ScreenState
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context
) : AuthBaseViewModel(appCtx) {

    fun logIn(email: String, password: String) = viewModelScope.launch {
        _authScreenState.emit(ScreenState(isLoading = true))
        if (checkIfIsEmpty(email) || checkIfIsEmpty(password)) {
            _authScreenState.emit(ScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields)))
        } else {
            if (validateEmail(email)) {
                handleResponse(authRepository.logIn(email, password), _authScreenState)
            } else {
                _authScreenState.emit(ScreenState(errorText = resourcesProvider.getString(string.please_enter_valid_email)))
            }
        }
    }
}