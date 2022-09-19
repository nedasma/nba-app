package com.example.nbaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.data.TeamRepositoryImpl
import com.example.nbaapp.data.models.Match
import com.example.nbaapp.data.models.Player
import com.example.nbaapp.data.models.Team
import com.example.nbaapp.utils.DispatcherProvider
import com.example.nbaapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main application view model which stores the majority of the business logic. It has the app [repo]
 * and the coroutine [dispatchers] injected via the constructor.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    private val repo: TeamRepositoryImpl,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class TeamEvent {
        class Success(val data: List<Team>) : TeamEvent()
        class Error(val error: String) : TeamEvent()
        object Empty : TeamEvent()
    }

    sealed class PlayerEvent {
        class Success(val data: List<Player>) : PlayerEvent()
        class Error(val error: String) : PlayerEvent()
        object Empty : PlayerEvent()
    }

    sealed class GamesEvent {
        class Success(val data: List<Match>) : GamesEvent()
        class Error(val error: String) : GamesEvent()
        object Empty : GamesEvent()
    }

    private val _teamData = MutableStateFlow<TeamEvent>(TeamEvent.Empty)
    val teamData: StateFlow<TeamEvent> = _teamData

    private val _gamesData = MutableStateFlow<GamesEvent>(GamesEvent.Empty)
    val gamesData: StateFlow<GamesEvent> = _gamesData

    private val _playerData = MutableStateFlow<PlayerEvent>(PlayerEvent.Empty)
    val playerData: StateFlow<PlayerEvent> = _playerData

    private val categories = listOf("Name", "City", "Conference")
    private var page = 1
    private val limit = 100

    /**
     * Loads all teams onto the view.
     */
    fun loadTeams() {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.getAllTeams()) {
                is Result.Error -> _teamData.value = TeamEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data?.data
                    _teamData.value = TeamEvent.Success(data!!)
                }
            }
        }
    }

    /**
     * Loads all players onto the view.
     */
    fun loadPlayers() {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.getAllPlayers(page, limit)) {
                is Result.Error -> _playerData.value = PlayerEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data?.data
                    _playerData.value = PlayerEvent.Success(data!!)
                }
            }
        }
    }

    /**
     * Passes the [searchQuery] to the repo so that the results from the search query can be obtained
     * and returned back to the view.
     */
    fun searchPlayers(searchQuery: String) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.searchForPlayers(searchQuery)) {
                is Result.Error -> _playerData.value = PlayerEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data?.data
                    _playerData.value = PlayerEvent.Success(data!!)
                }
            }
        }
    }

    /**
     * Passes the [teamId] to the repo so that all matches belonging to that team can be obtained
     * and displayed in the view.
     */
    fun getTeamMatchesById(teamId: Int) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.getTeamMatches(teamId, page, limit)) {
                is Result.Error -> _gamesData.value = GamesEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data?.data
                    _gamesData.value = GamesEvent.Success(data!!)
                }
            }
        }
    }

    /**
     * Passes the [teamId] to the repo so that a team name can be received by its ID.
     */
    fun getTeamNameById(teamId: Int) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.getAllTeams()) {
                is Result.Error -> _teamData.value = TeamEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data?.data
                    val filteredData = data?.filter { it.id == teamId }
                    if (filteredData.isNullOrEmpty()) {
                        _teamData.value = TeamEvent.Error("Cannot find team by given ID")
                    } else {
                        _teamData.value = TeamEvent.Success(filteredData)
                    }
                }
            }
        }
    }

    /**
     * Passes the [playerId] to the repo so that all the data regarding the player can be returned
     * by its ID and displayed in the view.
     */
    fun getPlayerById(playerId: Int) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = repo.getPlayerById(playerId)) {
                is Result.Error -> _playerData.value = PlayerEvent.Error(response.message!!)
                is Result.Success -> {
                    val data = response.data
                    _playerData.value = PlayerEvent.Success(listOf(data!!))
                }
            }
        }
    }

    /**
     * Sorts the list of teams (the [data]) by the category position.
     * @return sorted data
     */
    fun sortTeams(position: Int, data: List<Team>) : List<Team> {
        return when (categories[position]) {
            "Name" -> {
                data.sortedWith(compareBy { it.full_name })
            }
            "City" -> {
                data.sortedWith(compareBy { it.city })
            }
            "Conference" -> {
                data.sortedWith(compareBy { it.conference })
            }
            else -> {
                data
            }
        }
    }

    /**
     * Returns sorted [categories] as a list of strings.
     */
    fun getSortingCategories(): List<String> {
        return categories
    }
}