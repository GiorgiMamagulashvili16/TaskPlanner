package com.example.taskplanner.presentation.authorization.login_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
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
    authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context
) : AuthBaseViewModel(appCtx, authRepository) {

    private val _loginScreenState = MutableStateFlow(ScreenState<AuthResult>())
    val loginScreenState: StateFlow<ScreenState<AuthResult>> = _loginScreenState

    fun logIn(email: String, password: String) = viewModelScope.launch {
        _loginScreenState.emit(ScreenState(isLoading = true))
        logIn(
            _loginScreenState,
            User(email = email, password = password)
        )
    }
}