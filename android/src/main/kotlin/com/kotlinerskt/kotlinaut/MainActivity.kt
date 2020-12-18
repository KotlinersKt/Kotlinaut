package com.kotlinerskt.kotlinaut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlinerskt.kotlinaut.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val operator: Operator = Operator()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    suspend fun loQueSea(){
        operator.startMission()
    }
}
