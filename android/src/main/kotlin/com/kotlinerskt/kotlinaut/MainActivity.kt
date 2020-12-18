package com.kotlinerskt.kotlinaut

import android.app.Activity
import android.os.Bundle
import com.kotlinerskt.kotlinaut.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private val operator: Operator = Operator()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        operator.startMission()
    }
}
