package com.example.nbaapp.data.models

/**
 * Response data class for mapping responses with the actual data. The [data] is a List of
 * [Team] data objects.
 */
data class TeamResponse(
    val data: List<Team>,
)