package com.example.taskplanner.presentation.base

import android.content.Context
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.screen_state.ScreenState
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class AuthBaseViewModel @Inject constructor(@ApplicationContext appCtx: Context) :
    BaseViewModel(ResourcesProvider(appCtx)) {

    protected val _authScreenState = MutableStateFlow(ScreenState<AuthResult>())
    val authScreenState: StateFlow<ScreenState<AuthResult>> = _authScreenState
}