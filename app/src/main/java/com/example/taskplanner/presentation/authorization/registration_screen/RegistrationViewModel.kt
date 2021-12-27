package com.example.taskplanner.presentation.authorization.registration_screen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.User
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.util.ResourcesProvider
import com.example.taskplanner.data.util.ValidatorHelper
import com.example.taskplanner.presentation.base.BaseViewModel
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
    @ApplicationContext appCtx: Context,
    validatorHelper: ValidatorHelper
) : BaseViewModel(ResourcesProvider(appCtx), validatorHelper) {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setImageUri(imageUri: Uri) = viewModelScope.launch {
        _imageUri.emit(imageUri)
    }

    private val _registrationScreenState = MutableStateFlow(ScreenState<AuthResult>())
    val registrationScreenState: StateFlow<ScreenState<AuthResult>> = _registrationScreenState

    fun signUp(user: User) =
        viewModelScope.launch {
            _registrationScreenState.emit(ScreenState(isLoading = true))
            with(user) {
                if (validatorHelper.checkParamsIsNotBlank(
                        listOf(
                            username!!,
                            email!!,
                            password!!,
                            repeatedPassword!!,
                        )
                    ) {
                        emitFlowErrorState(
                            _registrationScreenState,
                            it
                        )
                    } && validatorHelper.checkEmailIsValid(email) {
                        emitFlowErrorState(
                            _registrationScreenState,
                            it
                        )
                    } && validatorHelper.checkPasswordsAreSame(
                        password,
                        repeatedPassword
                    ) {
                        emitFlowErrorState(
                            _registrationScreenState,
                            it
                        )
                    }
                ) {
                    handleResponse(authRepository.signUp(user), _registrationScreenState)
                }
            }
        }
}