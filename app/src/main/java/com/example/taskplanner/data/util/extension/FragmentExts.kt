package com.example.taskplanner.data.util.extension

import android.Manifest
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun Fragment.createSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    snackBarFunction: Snackbar.() -> Unit
) {
    val snackBar = Snackbar.make(requireView(), message, length)
    snackBar.snackBarFunction()
    snackBar.show()
}

fun Snackbar.snackAction(textColor: Int? = null, action: String, listener: (View) -> Unit) {
    setAction(action, listener)
    textColor?.let { setTextColor(textColor) }

}

fun <T> Fragment.observeData(liveData: LiveData<T>, observer: (it: T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner, { observer(it) })
}

fun <T> Fragment.flowObserver(flow: Flow<T>, observer: (t: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collect { observer(it) }
    }
}

fun Fragment.checkMediaPermissions(action: () -> Unit):
        ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perm ->
        if (perm[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
            perm[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
        ) {
            action.invoke()
        }
    }
}