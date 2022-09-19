package com.example.nbaapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.MainActivity
import com.example.nbaapp.R
import com.example.nbaapp.ui.AppViewModel
import com.example.nbaapp.ui.adapters.PlayerAdapter
import com.example.nbaapp.ui.adapters.TeamListAdapter
import kotlinx.coroutines.*

private const val TAG = "PlayerFragment"
private const val SEARCH_DELAY = 500L

/**
 * Fragment which the displays all the data related to the player(s).
 */
class PlayerFragment : Fragment() {
    lateinit var viewModel: AppViewModel
    lateinit var adapter: TeamListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playersList = view.findViewById<RecyclerView>(R.id.rv_players_list)
        val searchbar = view.findViewById<EditText>(R.id.search_bar)

        viewModel.loadPlayers()

        var job: Job? = null
        searchbar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch(Dispatchers.Main) {
                delay(SEARCH_DELAY)
                editable?.let { viewModel.searchPlayers(editable.toString()) }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.playerData.collect { event ->
                when (event) {
                    is AppViewModel.PlayerEvent.Success -> {
                        val adapter = PlayerAdapter(event.data, false)
                        playersList.adapter = adapter
                        playersList.layoutManager = LinearLayoutManager(context)
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
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayerFragment()
    }
}