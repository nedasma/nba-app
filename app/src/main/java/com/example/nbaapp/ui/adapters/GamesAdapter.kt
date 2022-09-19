package com.example.nbaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.R
import com.example.nbaapp.data.models.Match
import com.example.nbaapp.databinding.HeaderGamesBinding
import com.example.nbaapp.databinding.ItemGamesBinding
import com.example.nbaapp.utils.ViewType

class GamesAdapter(
    private val games: List<Match>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class GamesListViewHolder(val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root)
    inner class GamesListHeaderViewHolder(val binding: HeaderGamesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return if (viewType == ViewType.HEADER.ordinal) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HeaderGamesBinding.inflate(inflater, parent, false)
            GamesListHeaderViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGamesBinding.inflate(inflater, parent, false)
            GamesListViewHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GamesListHeaderViewHolder) {
            holder.binding.apply {
                homeNameHeader.text = holder.binding.root.resources.getString(R.string.home_name_header)
                homeScoreHeader.text = holder.binding.root.resources.getString(R.string.home_score_header)
                awayNameHeader.text = holder.binding.root.resources.getString(R.string.away_name_header)
                awayScoreHeader.text = holder.binding.root.resources.getString(R.string.away_score_header)
            }
        } else if (holder is GamesListViewHolder){
            holder.binding.apply {
                val game = games[position - 1]

                teamHomeName.text = game.home_team.full_name
                teamHomeScore.text = game.home_team_score.toString()
                awayHomeName.text = game.visitor_team.full_name
                awayHomeScore.text = game.visitor_team_score.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return games.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.ITEM.ordinal
        }
    }
}