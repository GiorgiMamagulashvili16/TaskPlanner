package com.example.taskplanner.data.util.extension

import android.Manifest
import android.content.Context
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.taskplanner.data.util.Constants
import com.example.taskplanner.presentation.date_picker.DatePickerFragment
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

fun <T> Fragment.liveDataObserver(liveData: LiveData<T>, observer: (it: T) -> Unit) {
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
fun Fragment.setDatePicker(action: (date:String) -> Unit){
    val datePicker = DatePickerFragment()
    requireActivity().supportFragmentManager.setFragmentResultListener(
        Constants.BUNDLE_REQUEST_KEY,
        viewLifecycleOwner
    ) { requestKey, bundle ->
        if (requestKey == Constants.BUNDLE_REQUEST_KEY) {
            val date = bundle.getString(Constants.SELECTED_DATE_STRING_KEY).toString()
            action.invoke(date)
        }
    }
    datePicker.show(requireActivity().supportFragmentManager, Constants.DATE_PICKER_FRAGMENT_TAG)
}

fun Fragment.enableOrDisableViews(vararg views: View) {
    views.forEach { view ->
        view.isEnabled = !view.isEnabled
    }
}

fun Fragment.viewsVisibilityChanger(vararg views: View) {
    views.forEach { view ->
        view.isVisible = !view.isVisible
    }
}

fun Fragment.changeSameViewBackground(
    vararg views: View,
    isEnabled: Boolean,
    enabledBackground: Int,
    defaultBackground: Int
) {
    views.forEach { view ->
        view.setBackgroundResource(
            if (isEnabled)
                enabledBackground
            else
                defaultBackground
        )
    }
}