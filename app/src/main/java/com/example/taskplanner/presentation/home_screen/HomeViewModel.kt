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
}