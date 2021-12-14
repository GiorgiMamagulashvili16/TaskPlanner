package com.example.taskplanner.presentation.base

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.util.Resource
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
open class AuthBaseViewModel @Inject constructor(@ApplicationContext appCtx: Context) :
    BaseViewModel(ResourcesProvider(appCtx)) {

    fun userAuth(
        flow: MutableStateFlow<ScreenState<AuthResult>>,
        action: Resource<AuthResult>,
        email: String,
        authParams: List<String>
    ) =
        viewModelScope.launch {
            if (checkParams(authParams, flow) && checkEmail(email, flow)) {
                handleResponse(action, flow)
            }
        }

    private fun checkParams(
        authParams: List<String>,
        flow: MutableStateFlow<ScreenState<AuthResult>>
    ): Boolean {
        authParams.forEach {
            if (it.isBlank()) {
                flow.value =
                    ScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields))
                return false
            }
        }
        return true
    }

    private fun checkEmail(
        email: String,
        flow: MutableStateFlow<ScreenState<AuthResult>>
    ): Boolean {
        if (!email.isValidEmail()) {
            flow.value = ScreenState(
                errorText = resourcesProvider.getString(
                    string.please_enter_valid_email
                )
            )
            return false
        }
        return true
    }
}