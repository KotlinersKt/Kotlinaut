package com.kotlinerskt.kotlinaut.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kotlinerskt.kotlinaut.databinding.FragmentLoginBinding
import com.kotlinerskt.kotlinaut.login.data.GRPCLoginRepository
import com.kotlinerskt.kotlinaut.login.usecase.NewAdventureUseCase
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class LogInFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(NewAdventureUseCase(GRPCLoginRepository()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        setupView(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    LoginViewModel.State.Idle -> Unit
                    LoginViewModel.State.Loading -> showSnackMessage(view, "Loading")
                    LoginViewModel.State.StartAdventure -> Timber.i("Start Adventure Please")
                    is LoginViewModel.State.Error -> {
                        showSnackMessage(view, state.errorMessage)
                    }
                }
            }
        }
    }

    private fun setupView(binding: FragmentLoginBinding) {
        with(binding) {
            root.setOnClickListener {
                userId.text = null
                userIdGroup.visibility = View.GONE
            }
            showUserId.setOnClickListener {
                userIdGroup.visibility = View.VISIBLE
            }
            startAdventure.setOnClickListener {
                val userId: String = userId.editableText.toString()
                viewModel.logInUser(userId)
            }
            resumeAdventure.setOnClickListener {
                showSnackMessage(root, "TBD")
            }
        }
    }

    private fun showSnackMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}