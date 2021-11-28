package com.example.taskplanner.presentation.authorization.registration_screen

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.extension.isValidEmail
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val _screenState = MutableLiveData<AuthScreenState>()
    val screenState: LiveData<AuthScreenState> = _screenState

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setImageUri(imageUri: Uri) = viewModelScope.launch {
        _imageUri.emit(imageUri)
    }

    fun signUp(username: String, password: String, email: String, job: String,imageUri: Uri) =
        viewModelScope.launch {
            _screenState.postValue(AuthScreenState.Loading(true))
            if (username.trim().isBlank() || password.trim().isBlank() || email.trim().isBlank()) {
                _screenState.postValue(AuthScreenState.Error(resourcesProvider.getString(string.please_fill_all_fields)))
            } else {
                if (email.isValidEmail()) {
                    handleSignUpResponse(authRepository.signUp(username, password, email, job,imageUri))
                } else {
                    _screenState.postValue(
                        AuthScreenState.Error(
                            resourcesProvider.getString(
                                string.please_enter_valid_email
                            )
                        )
                    )
                }
            }
        }

    private fun handleSignUpResponse(response: Resource<AuthResult>) = viewModelScope.launch {
        when (response) {
            is Resource.Success -> {
                _screenState.postValue(AuthScreenState.Success)
            }
            is Resource.Error -> {
                _screenState.postValue(response.errorMessage?.let { AuthScreenState.Error(it) })
            }
        }
    }
}