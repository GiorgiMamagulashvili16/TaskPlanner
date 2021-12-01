package com.example.taskplanner.data.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context, private val activity: Activity) {
    fun mediaPermissionRequest(
        positiveAction: () -> Unit,
        mediaPermissionCheckerAction: () -> Unit,
        actionWhenPermissionIsDenied: () -> Unit
    ) {
        when {
            hasReadExtStoragePermission() && hasWriteExtStoragePermission() -> {
                positiveAction.invoke()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                actionWhenPermissionIsDenied.invoke()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                actionWhenPermissionIsDenied.invoke()
            }
            else -> {
                mediaPermissionCheckerAction.invoke()
            }
        }
    }

    private fun hasWriteExtStoragePermission() = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExtStoragePermission() = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

}