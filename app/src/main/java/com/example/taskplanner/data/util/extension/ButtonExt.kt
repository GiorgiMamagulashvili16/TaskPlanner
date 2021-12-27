package com.example.taskplanner.data.util.extension

import android.content.Context
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources


fun ImageButton.setDrawableImage(context: Context, drawable: Int) {
    this.setImageDrawable(AppCompatResources.getDrawable(context, drawable))
}