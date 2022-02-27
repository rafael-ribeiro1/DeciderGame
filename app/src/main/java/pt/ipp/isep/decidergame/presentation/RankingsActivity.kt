package pt.ipp.isep.decidergame.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipp.isep.decidergame.*
import pt.ipp.isep.decidergame.RANKING_GAME_TIME
import pt.ipp.isep.decidergame.RANKING_NUM_MOVES
import pt.ipp.isep.decidergame.databinding.ActivityRankingsBinding
import pt.ipp.isep.decidergame.presentation.adapter.RankingAdapter
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

        binding.rankingList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = RankingAdapter(RANKING_GAME_TIME)
        }

        viewModel.rankingLD.observe(this) {
            val adapter = binding.rankingList.adapter as RankingAdapter
            adapter.submitList(it)
        }

        viewModel.gameTimeRanking()

        binding.toggleBtnRanking.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val adapter = binding.rankingList.adapter as RankingAdapter
                when (checkedId) {
                    R.id.btn_ranking_game_time -> {
                        viewModel.gameTimeRanking()
                        adapter.rankingType = RANKING_GAME_TIME
                        binding.rankingList.adapter = adapter
                    }
                    R.id.btn_ranking_num_moves -> {
                        viewModel.numMovesRanking()
                        adapter.rankingType = RANKING_NUM_MOVES
                        binding.rankingList.adapter = adapter
                    }
                    R.id.btn_ranking_score_peak -> {
                        viewModel.scorePeakRanking()
                        adapter.rankingType = RANKING_SCORE_PEAK
                        binding.rankingList.adapter = adapter
                    }
                }
            }
        }
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