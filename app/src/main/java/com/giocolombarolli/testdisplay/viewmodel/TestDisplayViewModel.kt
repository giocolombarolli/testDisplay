package com.giocolombarolli.testdisplay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TestDisplayViewModel : ViewModel() {

    private val _greenSquaresCount = MutableStateFlow(0)
    val greenSquaresCount: StateFlow<Int> = _greenSquaresCount

    private val _onTimeout = MutableSharedFlow<Unit>()
    val onTimeout = _onTimeout.asSharedFlow()

    var totalSquares: Int = 0
        private set

    fun setTotalSquares(count: Int) {
        totalSquares = count
    }

    fun incrementGreenSquares() {
        _greenSquaresCount.value += 1
    }

    fun notifyTimeoutReached() {
        viewModelScope.launch {
            _onTimeout.emit(Unit)
        }
    }
}