package com.example.taskplanner.presentation.splash_screen

import android.util.Log.d
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.data.util.extension.liveDataObserver
import com.example.taskplanner.data.util.extension.setAfterAnimOver
import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.Inflate
import com.example.taskplanner.presentation.project_screen.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {
    override fun inflateFragment(): Inflate<SplashFragmentBinding> = SplashFragmentBinding::inflate

    override fun getVmClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onBindViewModel(viewModel: SplashViewModel) {
        viewModel.setUserState()
        observeUserState(viewModel)
        val status = Status.values()[0].title
        d("PPRogress", "${getString(status)}")
    }

    private fun observeUserState(viewModel: SplashViewModel) {
        liveDataObserver(viewModel.isUserLogged) { isLogged ->
            binding.root.setAfterAnimOver {
                findNavController().navigate(
                    if (isLogged)
                        R.id.action_splashFragment_to_homeFragment
                    else
                        R.id.action_splashFragment_to_loginFragment
                )
            }
        }
    }
}