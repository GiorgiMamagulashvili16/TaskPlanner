package com.example.taskplanner.data.util.extension

import android.content.Context
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
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

fun View.changeVisibilityByParam(visibilityParam: Boolean) {
    isVisible = visibilityParam
}

fun ImageButton.setDrawableImage(context: Context, drawable: Int) {
    this.setImageDrawable(AppCompatResources.getDrawable(context, drawable))
}