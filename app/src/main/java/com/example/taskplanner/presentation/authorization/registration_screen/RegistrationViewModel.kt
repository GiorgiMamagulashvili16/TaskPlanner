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
    private val authRepository: AuthRepositoryImpl,
    @ApplicationContext appCtx: Context
) : AuthBaseViewModel(appCtx) {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setImageUri(imageUri: Uri) = viewModelScope.launch {
        _imageUri.emit(imageUri)
    }

    fun signUp(user: User, password: String) =
        viewModelScope.launch {
            _authScreenState.emit(ScreenState(isLoading = true))
            if (checkIfIsEmpty(user.username!!) || checkIfIsEmpty(password) || checkIfIsEmpty(user.email!!)
            ) {
                _authScreenState.emit(ScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields)))
            } else {
                if (validateEmail(user.email)) {
                    handleResponse(
                        authRepository.signUp(
                            user,
                            password
                        ),
                        _authScreenState
                    )
                } else {
                    _authScreenState.emit(
                        ScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_enter_valid_email
                            )
                        )
                    )
                }
            }
        }
}