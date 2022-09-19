package com.example.nbaapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.R
import com.example.nbaapp.data.models.Team
import com.example.nbaapp.databinding.HeaderTeamBinding
import com.example.nbaapp.databinding.ItemTeamBinding
import com.example.nbaapp.ui.fragments.GamesFragment
import com.example.nbaapp.utils.ViewType

class TeamListAdapter(
    private val teams: List<Team>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TeamListViewHolder(val binding: ItemTeamBinding) : RecyclerView.ViewHolder(binding.root)
    inner class TeamListHeaderViewHolder(val binding: HeaderTeamBinding) : RecyclerView.ViewHolder(binding.root)

    // region overridden methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return if (viewType == ViewType.HEADER.ordinal) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HeaderTeamBinding.inflate(inflater, parent, false)
            TeamListHeaderViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTeamBinding.inflate(inflater, parent, false)
            TeamListViewHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TeamListHeaderViewHolder) {
            holder.binding.apply {
                teamHeaderTitle.text = holder.binding.root.resources.getString(R.string.header_name)
                teamHeaderCity.text = holder.binding.root.resources.getString(R.string.header_city)
                teamHeaderConference.text = holder.binding.root.resources.getString(R.string.header_conference)
            }
        } else if (holder is TeamListViewHolder){
            holder.binding.apply {
                val team = teams[position - 1]

                teamTitle.text = team.full_name
                teamCity.text = team.city
                teamConference.text = team.conference
                holder.itemView.setOnClickListener {
                    loadFragment(GamesFragment.newInstance(team.id, false), it.context)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return teams.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.ITEM.ordinal
        }
    }

    // endregion

    /**
     * Helper function to create (load) a new [fragment].
     */
    private fun loadFragment(fragment: Fragment, context: Context) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}