package com.example.taskplanner.presentation.authorization.registration_screen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.base.AuthBaseViewModel
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
) : AuthBaseViewModel(ResourcesProvider(appCtx)) {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setImageUri(imageUri: Uri) = viewModelScope.launch {
        _imageUri.emit(imageUri)
    }

    fun signUp(username: String, password: String, email: String, job: String, imageUri: Uri) =
        viewModelScope.launch {
            _screenState.emit(AuthScreenState(isLoading = true))
            if (validateIfIsEmpty(username) || validateIfIsEmpty(password) || validateIfIsEmpty(
                    email
                )
            ) {
                _screenState.emit(AuthScreenState(errorText = resourcesProvider.getString(string.please_fill_all_fields)))
            } else {
                if (validateEmail(email)) {
                    handleResponse(
                        authRepository.signUp(
                            username,
                            password,
                            email,
                            job,
                            imageUri
                        )
                    )
                } else {
                    _screenState.emit(
                        AuthScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_enter_valid_email
                            )
                        )
                    )
                }
            }
        }
}