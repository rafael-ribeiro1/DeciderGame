package pt.ipp.isep.decidergame.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pt.ipp.isep.decidergame.RANKING_GAME_TIME
import pt.ipp.isep.decidergame.RANKING_NUM_MOVES
import pt.ipp.isep.decidergame.RANKING_SCORE_PEAK
import pt.ipp.isep.decidergame.data.persistence.model.Record
import pt.ipp.isep.decidergame.databinding.ItemRankingBinding
import java.text.DateFormat
import java.util.*

class RankingAdapter (var rankingType: Int = RANKING_GAME_TIME, private val onClick: (Record) -> Unit) :
    ListAdapter<Record, RankingAdapter.RankingViewHolder>(DiffCallback()) {

    inner class RankingViewHolder(private val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {
            val formatter = DateFormat.getDateTimeInstance()
            binding.tvItemDatetime.text = formatter.format(Date(record.datetime))
            binding.tvItemRecord.text = when (rankingType) {
                RANKING_GAME_TIME -> String.format("%.1fs", record.gameTime / 1000.0)
                RANKING_NUM_MOVES -> "${record.numMoves} moves"
                RANKING_SCORE_PEAK -> "${record.scorePeak}"
                else -> { return }
            }

            binding.root.setOnClickListener { onClick(record) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRankingBinding.inflate(inflater, parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Record>() {

        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.datetime == newItem.datetime
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }
}