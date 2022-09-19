package com.example.nbaapp.data.models

/**
 * Data class to store all data related to the single player.
 * @param first_name the name of the player
 * @param last_name the surname of the player
 * @param height_feet player's height in ft (can be null)
 * @param height_inches player's height in in (after ft) (can be null)
 * @param position player's playing position (Center, Shooting Guard, Point Guard, Small Forward or Power Forward)
 * @param team the team the player plays in
 * @param weight_pounds player's weight in lbs (can be null)
 * @param id the ID of the data item
 */
data class Player(
    val first_name: String,
    val height_feet: Any?,
    val height_inches: Any?,
    val id: Int,
    val last_name: String,
    val position: String,
    val team: Team,
    val weight_pounds: Any?
)