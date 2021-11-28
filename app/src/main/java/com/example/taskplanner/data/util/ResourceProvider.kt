package com.example.taskplanner.data.util

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ResourcesProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @NonNull
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }
}