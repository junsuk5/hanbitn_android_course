package com.surivalcoding.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.surivalcoding.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: MainViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // UI 업데이트
                    binding.secTextView.text = state.seconds.toString()
                    binding.milliTextView.text = state.millis.toString()

                    binding.lapLayout.removeAllViews()
                    for (lapTime in state.lapTimes) {
                        val textView = TextView(this@MainActivity)
                        textView.text = lapTime
                        binding.lapLayout.addView(textView, 0)
                    }

                    if (state.isRunning) {
                        binding.playFab.setImageResource(R.drawable.baseline_pause_24)
                    } else {
                        binding.playFab.setImageResource(R.drawable.baseline_play_arrow_24)
                    }
                }
            }
        }

        binding.playFab.setOnClickListener {
            viewModel.onClickPlayAndPauseButton()
        }

        binding.lapButton.setOnClickListener {
            viewModel.recordLapTime()
        }

        binding.resetFab.setOnClickListener {
            viewModel.reset()
        }
    }
}