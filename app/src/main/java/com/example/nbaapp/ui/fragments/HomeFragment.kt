package com.example.nbaapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.MainActivity
import com.example.nbaapp.R
import com.example.nbaapp.ui.AppViewModel
import com.example.nbaapp.ui.adapters.TeamListAdapter

private const val TAG = "HomeFragment"

/**
 * Fragment which acts as a starting point for the application. As a starter, it displays all teams
 * which can be sorted by clicking the "name" button.
 */
class HomeFragment : Fragment() {

    lateinit var viewModel: AppViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.getSortingCategories()
        )

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val teamsList = view.findViewById<RecyclerView>(R.id.rv_teams_list)
        val orderTypeBtn = view.findViewById<Button>(R.id.orderTypeBtn)

        spinner.adapter = spinnerAdapter

        viewModel.loadTeams()

        lifecycleScope.launchWhenStarted {
            viewModel.teamData.collect { event ->
                when (event) {
                    is AppViewModel.TeamEvent.Success -> {
                        val adapter = TeamListAdapter(event.data)
                        teamsList.adapter = adapter
                        teamsList.layoutManager = LinearLayoutManager(context)

                        orderTypeBtn.setOnClickListener {
                            spinner.visibility = View.VISIBLE
                            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                @SuppressLint("NotifyDataSetChanged")
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    teamsList.adapter = TeamListAdapter(
                                        viewModel.sortTeams(position, event.data)
                                    )
                                    (teamsList.adapter as TeamListAdapter).notifyDataSetChanged()
                                    spinner.visibility = View.INVISIBLE
                                }
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    // do nothing
                                }
                            }
                        }


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

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}