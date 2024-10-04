package com.surivalcoding.stopwatch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import kotlin.concurrent.timer

data class MainUiState(
    val millis: Int = 0,
    val seconds: Int = 0,
    val isRunning: Boolean = false,
    val lapTimes: List<String> = emptyList(),
)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var time = 0
    private var timerTask: Timer? = null
    private var lap = 1

    private fun pause() {
        _uiState.update {
            it.copy(isRunning = false)
        }
        timerTask?.cancel()
    }

    private fun start() {
        _uiState.update {
            it.copy(isRunning = true)
        }

        timerTask = timer(period = 10) {
            time++

            _uiState.update {
                it.copy(
                    seconds = time / 100,
                    millis = time % 100,
                )
            }
        }
    }

    fun reset() {
        timerTask?.cancel()

        time = 0
        lap = 1
        _uiState.update {
            it.copy(
                seconds = 0,
                millis = 0,
                isRunning = false,
                lapTimes = emptyList(),
            )
        }
    }

    fun recordLapTime() {
        val lapTime = "$lap LAP: ${uiState.value.seconds}.${uiState.value.millis}"

        _uiState.update {
            it.copy(
                lapTimes = it.lapTimes + lapTime,
            )
        }
        lap++
    }

    fun onClickPlayAndPauseButton() {
        if (uiState.value.isRunning) {
            pause()
        } else {
            start()
        }
    }

}