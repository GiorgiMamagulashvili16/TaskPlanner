package com.example.taskplanner.data.util.extension

import android.content.Context
import com.example.taskplanner.presentation.project_screen.Status

fun Context.getStatusByOrdinal(ordinal: Int): Status {
    return Status.values()[ordinal]
}