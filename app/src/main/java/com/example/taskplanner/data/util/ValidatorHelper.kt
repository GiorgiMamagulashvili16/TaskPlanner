package com.example.taskplanner.data.util

import com.example.taskplanner.data.util.extension.isValidEmail
import com.example.taskplanner.presentation.authorization.registration_screen.string
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatorHelper @Inject constructor() {

    fun checkParamsIsBlank(
        params: List<String>,
        action: (message: Int) -> Unit
    ): Boolean {
        val result = params.filter { it.isBlank() }
        if (result.isNotEmpty()) {
            action.invoke(string.please_fill_all_fields)
        }
        return result.isEmpty()
    }

    fun checkEmail(
        email: String,
        action: (message: Int) -> Unit
    ): Boolean {
        return if (!email.isValidEmail()) {
            action.invoke(string.please_enter_valid_email)
            false
        } else {
            true
        }
    }

    fun checkPasswords(
        password: String,
        repeatedPassword: String,
        action: (message: Int) -> Unit
    ): Boolean {
        return if (password != repeatedPassword) {
            action.invoke(string.txt_repeat_password_error)
            false
        } else {
            true
        }
    }

    fun checkParamsIsNull(
        params: List<String?>,
        action: (message: Int) -> Unit
    ): Boolean {
        val result = params.filter { it == null }
        if (result.isNotEmpty()) {
            action.invoke(string.please_choose_estimate_time)
        }
        return result.isEmpty()
    }
}