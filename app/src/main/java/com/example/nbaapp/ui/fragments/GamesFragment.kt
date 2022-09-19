package com.example.nbaapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.MainActivity
import com.example.nbaapp.R
import com.example.nbaapp.ui.AppViewModel
import com.example.nbaapp.ui.adapters.GamesAdapter
import com.example.nbaapp.ui.adapters.PlayerAdapter

private const val TAG = "GamesFragment"

/**
 * Multi-purpose fragment which displays data for lots of players (with search functionality) OR
 * displays extended data on a single player once the item from the player list is clicked.
 */
class GamesFragment : Fragment() {
    lateinit var viewModel: AppViewModel
    private var itemId: Int = 0
    private var isPlayerData: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = (activity as MainActivity).viewModel
        arguments?.getInt("itemId")?.let { itemId = it }
        arguments?.getBoolean("isPlayerData")?.let { isPlayerData = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gamesList = view.findViewById<RecyclerView>(R.id.rv_games_list)
        val teamName = view.findViewById<TextView>(R.id.team_name_header)
        val home = view.findViewById<TextView>(R.id.home_btn)

        if (isPlayerData) {
            viewModel.getPlayerById(itemId)
            lifecycleScope.launchWhenStarted {
                viewModel.playerData.collect { event ->
                    when (event) {
                        is AppViewModel.PlayerEvent.Success -> {
                            val adapter = PlayerAdapter(event.data, isPlayerData)
                            gamesList.adapter = adapter
                            gamesList.layoutManager = LinearLayoutManager(context)
                            teamName.text = buildString {
                                append(event.data.first().first_name)
                                append(" ")
                                append(event.data.first().last_name)
                            }

                            home.setOnClickListener {
                                parentFragmentManager.popBackStack()
                            }
                        }
                        is AppViewModel.PlayerEvent.Error -> {
                            Log.e(TAG, event.error)
                        }
                        is AppViewModel.PlayerEvent.Empty -> {
                            // do nothing, not necessary to log this
                        }
                    }
                }
            }
        } else {
            viewModel.getTeamMatchesById(itemId)
            viewModel.getTeamNameById(itemId)

            lifecycleScope.launchWhenStarted {
                viewModel.gamesData.collect { event ->
                    when (event) {
                        is AppViewModel.GamesEvent.Success -> {
                            val adapter = GamesAdapter(event.data)
                            gamesList.adapter = adapter
                            gamesList.layoutManager = LinearLayoutManager(context)

                            home.setOnClickListener {
                                parentFragmentManager.popBackStack()
                            }
                        }
                        is AppViewModel.GamesEvent.Error -> {
                            Log.e(TAG, event.error)
                        }
                        is AppViewModel.GamesEvent.Empty -> {
                            // do nothing, not necessary to log this
                        }
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.teamData.collect { event ->
                    when (event) {
                        is AppViewModel.TeamEvent.Success -> {
                            teamName.text = event.data.first().full_name
                        }
                        is AppViewModel.TeamEvent.Error -> {
                            Log.e(TAG, event.error)
                        }
                        is AppViewModel.TeamEvent.Empty -> {
                            // do nothing, not necessary to log this
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(itemId: Int, isPlayerData: Boolean) = GamesFragment().apply {
            arguments = Bundle().apply {
                putInt("itemId", itemId)
                putBoolean("isPlayerData", isPlayerData)
            }
        }
    }
}