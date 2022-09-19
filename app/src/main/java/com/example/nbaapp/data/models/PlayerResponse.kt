package com.example.nbaapp.data.models

/**
 * Response data class for mapping responses with the actual data. The [data] is a List of
 * [Player] data objects.
 */
data class PlayerResponse(
    val data: List<Player>
)