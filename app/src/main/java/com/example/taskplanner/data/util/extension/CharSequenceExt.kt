package com.example.taskplanner.data.util.extension

import android.text.TextUtils
import android.util.Patterns

fun CharSequence.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}