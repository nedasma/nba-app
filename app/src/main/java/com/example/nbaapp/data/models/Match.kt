package com.example.nbaapp.data.models

/**
 * Data class to store all data related to the game one team has played
 * @param home_team the data of the [HomeTeam]
 * @param home_team_score points the home team has scored
 * @param visitor_team the data of the [VisitorTeam] (away team)
 * @param visitor_team_score points the away team has scored
 */
data class Match(
    val home_team: HomeTeam,
    val home_team_score: Int,
    val visitor_team: VisitorTeam,
    val visitor_team_score: Int
)