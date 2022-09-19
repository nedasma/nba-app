package com.example.nbaapp.data

import com.example.nbaapp.data.models.GamesResponse
import com.example.nbaapp.data.models.Player
import com.example.nbaapp.data.models.PlayerResponse
import com.example.nbaapp.data.models.TeamResponse
import com.example.nbaapp.utils.Result

/**
 * Repository containing all data related to the NBA API.
 * @see TeamRepositoryImpl for the implementation part of this interface.
 */
interface TeamRepository {
    suspend fun getAllTeams(): Result<TeamResponse>
    suspend fun getTeamMatches(id: Int, page: Int?, limit: Int?): Result<GamesResponse>
    suspend fun getAllPlayers(page: Int?, limit: Int?): Result<PlayerResponse>
    suspend fun getPlayerById(id: Int): Result<Player>
    suspend fun searchForPlayers(query: String): Result<PlayerResponse>
}