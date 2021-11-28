package com.example.taskplanner.presentation.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getCurrentUser() = viewModelScope.launch {
        handleResponse(authRepository.getCurrentUser())
    }

    private fun handleResponse(response: Resource<User>) = viewModelScope.launch {
        Log.d("TESDA", "${response.data}")
        when (response) {
            is Resource.Success -> {
                _user.postValue(response.data!!)
            }
            is Resource.Error -> {}
        }
    }
}