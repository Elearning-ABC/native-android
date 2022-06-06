package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class OnStartViewModel : ViewModel() {
    companion object {
        var viewModelStoreOwner = ViewModelStoreOwner { ViewModelStore() }
        var key = "OnStartViewModel"
    }

    private var ticker: Int = 0

    fun getTickerValue() : Int {
        return ticker
    }

    fun increaseTickerValue(value: Int) {
        ticker.plus(value)
    }
}