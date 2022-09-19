package com.example.nbaapp.data

import com.example.nbaapp.data.models.GamesResponse
import com.example.nbaapp.data.models.Player
import com.example.nbaapp.data.models.PlayerResponse
import com.example.nbaapp.data.models.TeamResponse
import com.example.nbaapp.utils.Result
import javax.inject.Inject

/**
 * Implementation class of the NBA API repository interface. The [api] itself is injected via the
 * constructor.
 */
class TeamRepositoryImpl @Inject constructor(
    private val api: NbaApi
) : TeamRepository {
    /**
     * Gets all teams from the list of NBA teams. Returns [Result.Success] if the response from the
     * API is successful, otherwise it returns a [Result.Error] with the error message.
     */
    override suspend fun getAllTeams(): Result<TeamResponse> {
        return try {
            val response = api.getAllTeams()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.Success(result)
            } else {
                Result.Error(response.message())
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Unknown error has occurred")
        }
    }

    /**
     * Gets all team matches by the team [id]. The request can be further chunked down into providing
     * [page]s and [limit]s. Returns [Result.Success] if the response from the API is successful,
     * otherwise it returns a [Result.Error] with the error message.
     */
    override suspend fun getTeamMatches(id: Int, page: Int?, limit: Int?): Result<GamesResponse> {
        return try {
            val response = api.getTeamMatches(id, page, limit)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.Success(result)
            } else {
                Result.Error(response.message())
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Unknown error has occurred")
        }
    }

    /**
     * Gets all players who have played in the NBA league. The request is paginated, that is, one must
     * provide a specific [page] and [limit] on which a certain number of players would be returned. Returns
     * [Result.Success] if the response from the API is successful, otherwise it returns a
     * [Result.Error] with the error message.
     */
    override suspend fun getAllPlayers(page: Int?, limit: Int?): Result<PlayerResponse> {
        return try {
            val response = api.getAllPlayers(page, limit)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.Success(result)
            } else {
                Result.Error(response.message())
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Unknown error has occurred")
        }
    }

    /**
     * Gets all player data by their [id]. Returns [Result.Success] if the response from the
     * API is successful, otherwise it returns a [Result.Error] with the error message.
     */
    override suspend fun getPlayerById(id: Int): Result<Player> {
        return try {
            val response = api.getPlayerById(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.Success(result)
            } else {
                Result.Error(response.message())
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Unknown error has occurred")
        }
    }

    /**
     * Performs a search of players on the provided [query]. Returns [Result.Success] if the response
     * from the API is successful, otherwise it returns a [Result.Error] with the error message.
     */
    override suspend fun searchForPlayers(query: String): Result<PlayerResponse> {
        return try {
            val response = api.searchForPlayers(query)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.Success(result)
            } else {
                Result.Error(response.message())
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Unknown error has occurred")
        }
    }

}