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
import com.kotlinerskt.kotlinaut.login.data.GRPCLocateRepository
import com.kotlinerskt.kotlinaut.login.data.GRPCLoginRepository
import com.kotlinerskt.kotlinaut.login.usecase.ChubyLocationUseCase
import com.kotlinerskt.kotlinaut.login.usecase.NewAdventureUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class LogInFragment : Fragment() {

    private var uiStateJob: Job? = null
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            NewAdventureUseCase(GRPCLoginRepository()),
            ChubyLocationUseCase(GRPCLocateRepository())
        )
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

    override fun onResume() {
        super.onResume()
        viewModel.locateChuby("v.2.0.1")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiStateJob = lifecycleScope.launchWhenStarted {
            viewModel.state
                .onCompletion {
                    Timber.i("Finishing")
                    showSnackMessage(view, "Finishing")
                }
                .collect { state ->
                    when (state) {
                        LoginViewModel.State.Idle -> Unit
                        LoginViewModel.State.Loading -> showSnackMessage(view, "Loading")
                        LoginViewModel.State.StartAdventure -> Timber.i("Start Adventure Please")
                        is LoginViewModel.State.Error -> {
                            showSnackMessage(view, state.errorMessage)
                        }
                        is LoginViewModel.State.ChubyLocation -> {
                            Timber.tag("ChubyTag").i("Chuby is here: ${state.chubyVisit.location}")
                        }
                    }
                }
        }
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
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