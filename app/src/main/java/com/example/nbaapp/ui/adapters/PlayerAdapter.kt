package com.example.nbaapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.R
import com.example.nbaapp.data.models.Player
import com.example.nbaapp.databinding.HeaderPlayerBinding
import com.example.nbaapp.databinding.ItemPlayerBinding
import com.example.nbaapp.ui.fragments.GamesFragment
import com.example.nbaapp.utils.ViewType

class PlayerAdapter(
    private val playerData: List<Player>,
    private val showExtendedPlayerData: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class PlayerListViewHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)
    inner class PlayerListHeaderViewHolder(val binding: HeaderPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    // region overridden methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return if (viewType == ViewType.HEADER.ordinal) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HeaderPlayerBinding.inflate(inflater, parent, false)
            PlayerListHeaderViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPlayerBinding.inflate(inflater, parent, false)
            PlayerListViewHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlayerListHeaderViewHolder) {
            holder.binding.apply {
                playerHeaderName.text = holder.binding.root.resources.getString(R.string.player_header_name)
                playerHeaderSurname.text = holder.binding.root.resources.getString(R.string.player_header_surname)
                playerHeaderTeam.text = holder.binding.root.resources.getString(R.string.player_header_team)
                if (showExtendedPlayerData) {
                    playerHeaderHeightFt.text = holder.binding.root.resources.getString(R.string.player_header_height_ft)
                    playerHeaderHeightIn.text = holder.binding.root.resources.getString(R.string.player_header_height_in)
                    playerHeaderPosition.text = holder.binding.root.resources.getString(R.string.player_header_position)
                    playerHeaderWeight.text = holder.binding.root.resources.getString(R.string.player_header_weight)

                    playerHeaderHeightFt.visibility = View.VISIBLE
                    playerHeaderHeightIn.visibility = View.VISIBLE
                    playerHeaderPosition.visibility = View.VISIBLE
                    playerHeaderWeight.visibility = View.VISIBLE
                }
            }
        } else if (holder is PlayerListViewHolder) {
            holder.binding.apply {
                val player = playerData[position - 1]

                playerFirstName.text = player.first_name
                playerLastName.text = player.last_name
                playerTeam.text = player.team.full_name

                if (showExtendedPlayerData) {
                    playerHeightFt.text = player.height_feet?.toString() ?: ""
                    playerHeightIn.text = player.height_inches?.toString() ?: ""
                    playerPosition.text = player.position ?: ""
                    playerWeight.text = player.weight_pounds?.toString() ?: ""

                    playerHeightFt.visibility = View.VISIBLE
                    playerHeightIn.visibility = View.VISIBLE
                    playerPosition.visibility = View.VISIBLE
                    playerWeight.visibility = View.VISIBLE
                }

                holder.itemView.setOnClickListener {
                   loadFragment(GamesFragment.newInstance(player.id, true), it.context)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return playerData.size + 1
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