package pt.ipp.isep.decidergame.presentation

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import pt.ipp.isep.decidergame.INITIAL_VALUE
import pt.ipp.isep.decidergame.R
import pt.ipp.isep.decidergame.data.model.Calculus
import pt.ipp.isep.decidergame.data.model.Operation
import pt.ipp.isep.decidergame.databinding.ActivityMainBinding
import pt.ipp.isep.decidergame.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var textViewDefaultColors: ColorStateList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textViewDefaultColors = binding.tvTimeLeft.textColors

        disableOpButtons()

        viewModel.scoreLD.observe(this) {
            binding.tvScore.text = it.toString()
        }
        viewModel.calculusPairLD.observe(this) {
            enableOpButtons(it.first, it.second)
        }
        viewModel.moveTimerLD.observe(this) {
            if (it != 0L) {
                binding.tvTimeLeft.text = String.format("%.1fs", it / 1000.0)
                if (it < 1500L) {
                    binding.tvTimeLeft.setTextColor(ContextCompat.getColor(applicationContext, R.color.warning_red))
                } else {
                    binding.tvTimeLeft.setTextColor(textViewDefaultColors)
                }
            } else {
                binding.tvTimeLeft.text = null
            }
        }

        binding.btnStart.setOnClickListener {
            viewModel.startGame()
            // TODO: Stop Game button
        }
    }

    private fun disableOpButtons() {
        binding.btnLeft.isClickable = false
        binding.btnRight.isClickable = false
        binding.btnLeft.text = Operation.values().joinToString(separator = "")
        binding.btnRight.text = Operation.values().joinToString(separator = "")
    }

    private fun enableOpButtons(op1: Calculus, op2: Calculus) {
        binding.btnLeft.isClickable = true
        binding.btnRight.isClickable = true
        binding.btnLeft.text = op1.toString()
        binding.btnRight.text = op2.toString()
    }
}