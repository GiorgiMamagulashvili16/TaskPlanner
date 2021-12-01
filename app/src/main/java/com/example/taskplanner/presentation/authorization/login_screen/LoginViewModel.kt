package com.example.taskplanner.presentation.authorization.login_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.authorization.registration_screen.AuthScreenState
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.AuthBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context
) : AuthBaseViewModel(ResourcesProvider(appCtx)) {

    fun logIn(email: String, password: String) = viewModelScope.launch {
        _screenState.emit(AuthScreenState(isLoading = true))
        if (validateIfIsEmpty(email) || validateIfIsEmpty(password)) {
            _screenState.emit(AuthScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields)))
        } else {
            if (validateEmail(email)) {
                handleResponse(authRepository.logIn(email, password))
            } else {
                _screenState.emit(AuthScreenState(errorText = resourcesProvider.getString(string.please_enter_valid_email)))
            }
        }
    }
}