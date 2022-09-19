package com.example.nbaapp.data.models

/**
 * Data class to store all data related to the away team.
 * @param abbreviation the initials of the team name
 * @param city the city the team plays in
 * @param conference the conference (East or West) the team belongs to
 * @param division the division in the conference the team belongs to
 * @param full_name the full name of the team
 * @param name the shortened form of the team name
 * @param id the ID of the data item
 */
data class VisitorTeam(
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val id: Int,
    val name: String
)