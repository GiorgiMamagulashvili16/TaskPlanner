package com.example.taskplanner.presentation.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.taskplanner.data.util.extension.createSnackBar
import com.example.taskplanner.data.util.extension.snackAction
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.google.android.material.snackbar.Snackbar

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[getVmClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateFragment().invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindViewModel(viewModel)
    }


    abstract fun inflateFragment(): Inflate<VB>
    abstract fun getVmClass(): Class<VM>
    abstract fun onBindViewModel(viewModel: VM)

    protected inline fun mediaPermissionRequest(
        crossinline positiveAction: () -> Unit,
        crossinline mediaPermissionCheckerAction: () -> Unit,
    ) {
        when {
            hasReadExtStoragePermission() && hasWriteExtStoragePermission() -> {
                positiveAction.invoke()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                createSnackBar(
                    getString(string.app_need_this_permission),
                    length = Snackbar.LENGTH_INDEFINITE
                ) {
                    snackAction(action = getString(string.ok)) {
                        mediaPermissionCheckerAction.invoke()
                    }
                }
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                createSnackBar(
                    getString(string.app_need_this_permission),
                    length = Snackbar.LENGTH_INDEFINITE
                ) {
                    snackAction(action = getString(string.ok)) {
                        mediaPermissionCheckerAction.invoke()
                    }
                }
            }
            else -> {
                mediaPermissionCheckerAction.invoke()
            }
        }
    }


    protected fun requestMediaPermissions(request: ActivityResultLauncher<Array<String>>) {
        request.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    protected fun hasWriteExtStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    protected fun hasReadExtStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

}