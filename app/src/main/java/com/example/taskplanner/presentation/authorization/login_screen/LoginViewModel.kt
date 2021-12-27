package com.example.taskplanner.presentation.authorization.login_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.ValidatorHelper
import com.example.taskplanner.presentation.base.BaseViewModel
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
    val authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context,
    validatorHelper: ValidatorHelper
) : BaseViewModel(ResourcesProvider(appCtx), validatorHelper) {

    private val _loginScreenState = MutableStateFlow(ScreenState<AuthResult>())
    val loginScreenState: StateFlow<ScreenState<AuthResult>> = _loginScreenState

    fun logIn(email: String, password: String) = viewModelScope.launch {
        _loginScreenState.emit(ScreenState(isLoading = true))
        if (validatorHelper.checkParamsIsNotBlank(
                listOf(
                    email,
                    password
                )
            ) { emitFlowErrorState(_loginScreenState, it) } && validatorHelper.checkEmailIsValid(email) {
                emitFlowErrorState(
                    _loginScreenState, it
                )
            }
        ) {
            handleResponse(authRepository.logIn(email, password), _loginScreenState)
        }
    }

}