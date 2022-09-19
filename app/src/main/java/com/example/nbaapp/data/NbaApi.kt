package com.example.nbaapp.data

import com.example.nbaapp.data.models.GamesResponse
import com.example.nbaapp.data.models.Player
import com.example.nbaapp.data.models.PlayerResponse
import com.example.nbaapp.data.models.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface to implement methods for retrieving data from the NBA games API. This can be easily
 * extended to include any API endpoints the API could provide.
 */
interface NbaApi {

    /**
     * GETs all teams from the list of NBA teams.
     */
    @GET("teams")
    suspend fun getAllTeams(): Response<TeamResponse>

    /**
     * GETs all team matches by the team [id]. The request can be further chunked down into providing
     * [page]s and [limit]s.
     */
    @GET("games")
    suspend fun getTeamMatches(
        @Query("team_ids[]")
        id: Int,
        @Query("page")
        page: Int?,
        @Query("per_page")
        limit: Int?
    ): Response<GamesResponse>

    /**
     * GETs all players who have played in the NBA league. The request is paginated, that is, one must
     * provide a specific [page] and [limit] on which a certain number of players would be returned.
     */
    @GET("players")
    suspend fun getAllPlayers(
        @Query("page")
        page: Int?,
        @Query("per_page")
        limit: Int?
    ): Response<PlayerResponse>

    /**
     * GETs all player data by their [id].
     */
    @GET("players/{id}")
    suspend fun getPlayerById(
        @Path("id")
        id: Int
    ): Response<Player>

    /**
     * Performs a search of players on the provided [query].
     */
    @GET("players")
    suspend fun searchForPlayers(
        @Query("search")
        query: String
    ): Response<PlayerResponse>
}