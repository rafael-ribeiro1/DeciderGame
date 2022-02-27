package pt.ipp.isep.decidergame.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pt.ipp.isep.decidergame.DeciderApplication
import pt.ipp.isep.decidergame.R
import pt.ipp.isep.decidergame.databinding.ActivityRankingsBinding
import pt.ipp.isep.decidergame.presentation.viewmodel.RankingsViewModel
import pt.ipp.isep.decidergame.presentation.viewmodel.RankingsViewModelFactory

class RankingsActivity : AppCompatActivity() {

    private val viewModel: RankingsViewModel by viewModels {
        RankingsViewModelFactory((application as DeciderApplication).recordRepository)
    }
    private lateinit var binding: ActivityRankingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.ranking)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}