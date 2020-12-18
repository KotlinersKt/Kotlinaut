package com.kotlinerskt.kotlinaut.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlinerskt.kotlinaut.databinding.FragmentLoginBinding

class LogInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}