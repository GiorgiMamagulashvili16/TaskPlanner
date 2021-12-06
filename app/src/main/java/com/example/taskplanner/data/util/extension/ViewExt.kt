package com.example.taskplanner.data.util.extension

import android.view.View
import androidx.core.view.isVisible

fun View.enableOrDisableView() {
    isEnabled = !isEnabled
}

fun View.changeVisibility() {
    isVisible = !isVisible
}

fun View.changeSameViewBackground(
    isEnabled: Boolean,
    enabledBackground: Int,
    defaultBackground: Int
) {
    this.setBackgroundResource(
        if (isEnabled)
            enabledBackground
        else
            defaultBackground
    )
}