package com.alva.codedelaroute.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OnStartViewModel: ViewModel() {

    private var ticker = mutableStateOf(0)

    fun getTickerValue(): Int {
        return ticker.value
    }

    fun increaseTickerValue(value: Int) {
        ticker.value += value
    }

}