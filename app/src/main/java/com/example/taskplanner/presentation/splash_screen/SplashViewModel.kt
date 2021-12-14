package com.example.taskplanner.presentation.splash_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.repository.auth.AuthRepository
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val authRepository: AuthRepository
) : BaseViewModel(ResourcesProvider(appCtx)) {
    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: LiveData<Boolean> = _isUserLogged

    fun setUserState() = viewModelScope.launch {
        _isUserLogged.postValue(authRepository.isUserLogged())
    }
}