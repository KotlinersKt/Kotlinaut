package com.kotlinerskt.kotlinaut

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    private val operator: Operator = Operator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    suspend fun loQueSea(){
        operator.startMission()
    }
}
