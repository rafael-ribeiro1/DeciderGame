package pt.ipp.isep.decidergame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import pt.ipp.isep.decidergame.INITIAL_VALUE
import pt.ipp.isep.decidergame.R
import pt.ipp.isep.decidergame.data.model.Operation
import pt.ipp.isep.decidergame.databinding.ActivityMainBinding
import pt.ipp.isep.decidergame.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvScore.text = INITIAL_VALUE.toString()
        binding.btnLeft.text = Operation.values().joinToString(separator = "")
        binding.btnRight.text = Operation.values().joinToString(separator = "")
    }
}