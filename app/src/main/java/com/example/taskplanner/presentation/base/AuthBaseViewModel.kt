package com.example.taskplanner.presentation.base

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.auth.AuthRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.extension.isValidEmail
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.screen_state.ScreenState
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthBaseViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val authRepository: AuthRepository
) : BaseViewModel(ResourcesProvider(appCtx)) {

    fun logIn(
        flow: MutableStateFlow<ScreenState<AuthResult>>,
        user: User
    ) = viewModelScope.launch {
        if (checkEmail(user.email!!, flow) && checkParams(
                listOf(user.email, user.password!!),
                flow
            )
        ) {
            handleResponse(
                authRepository.logIn(user.email, user.password), flow
            )
        }
    }

    fun signUp(
        flow: MutableStateFlow<ScreenState<AuthResult>>,
        user: User,
    ) = viewModelScope.launch {
        if (checkParams(
                listOf(user.email!!, user.password!!, user.username!!),
                flow
            ) && checkEmail(user.email, flow) && checkPasswords(
                user.password,
                user.repeatedPassword!!, flow
            )
        ) {
            handleResponse(authRepository.signUp(user), flow)
        }
    }

    private fun checkParams(
        authParams: List<String>,
        flow: MutableStateFlow<ScreenState<AuthResult>>
    ): Boolean {
        val result = authParams.filter { it.isBlank() }
        if (result.isNotEmpty()) {
            flow.value = ScreenState(
                errorText = resourcesProvider.getString(
                    string.please_fill_all_fields
                )
            )
        }
        return result.isEmpty()
    }

    private fun checkEmail(
        email: String,
        flow: MutableStateFlow<ScreenState<AuthResult>>
    ): Boolean {
        return if (!email.isValidEmail()) {
            flow.value = ScreenState(
                errorText = resourcesProvider.getString(
                    string.please_enter_valid_email
                )
            )
            false
        } else {
            true
        }
    }

    private fun checkPasswords(
        password: String,
        repeatedPassword: String,
        flow: MutableStateFlow<ScreenState<AuthResult>>
    ): Boolean {
        return if (password != repeatedPassword) {
            flow.value =
                ScreenState(errorText = resourcesProvider.getString(string.txt_repeat_password_error))
            false
        } else {
            true
        }
    }
}