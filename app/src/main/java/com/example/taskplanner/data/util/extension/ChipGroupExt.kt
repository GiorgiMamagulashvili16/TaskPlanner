package com.example.taskplanner.data.util.extension

import androidx.core.view.children
import com.google.android.material.chip.ChipGroup

fun ChipGroup.setChipsEnabledOrDisabled(enabled:Boolean) {
    children.forEach { it.isEnabled = enabled }
}
fun ChipGroup.setChipsDisabled(){
    children.forEach { it.isEnabled = false }
}