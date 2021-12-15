package com.example.taskplanner.presentation.authorization.registration_screen

import android.content.Context
import android.net.Uri
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
class RegistrationViewModel @Inject constructor(
    authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context
) : AuthBaseViewModel(appCtx, authRepository) {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setImageUri(imageUri: Uri) = viewModelScope.launch {
        _imageUri.emit(imageUri)
    }

    private val _registerScreenState = MutableStateFlow(ScreenState<AuthResult>())
    val registerScreenState: StateFlow<ScreenState<AuthResult>> = _registerScreenState


    fun signUp(user: User) =
        viewModelScope.launch {
            _registerScreenState.emit(ScreenState(isLoading = true))
            userAuth(
                _registerScreenState,
                user
            )
        }
}