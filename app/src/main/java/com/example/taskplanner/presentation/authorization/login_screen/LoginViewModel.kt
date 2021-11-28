package com.example.taskplanner.presentation.authorization.login_screen

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.Resource
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.extension.isValidEmail
import com.example.taskplanner.presentation.authorization.registration_screen.AuthScreenState
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository:AuthRepositoryImpl,
    private val resourcesProvider: ResourcesProvider
): ViewModel() {
    private val _screenState = MutableLiveData<AuthScreenState>()
    val screenState: LiveData<AuthScreenState> = _screenState

    fun logIn(email:String,password:String) = viewModelScope.launch {
        if (email.trim().isBlank() || password.trim().isBlank()){
            _screenState.postValue(AuthScreenState.Error(resourcesProvider.getString(string.please_fill_all_fields)))
        }else{
            if (email.isValidEmail()){
                handleLogInResponse(authRepository.logIn(email, password))
            }else{
                _screenState.postValue(AuthScreenState.Error(resourcesProvider.getString(string.please_enter_valid_email)))
            }
        }
    }
    private fun handleLogInResponse(response: Resource<AuthResult>) = viewModelScope.launch {

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