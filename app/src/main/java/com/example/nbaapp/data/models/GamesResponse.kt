package com.example.nbaapp.data.models

/**
 * Response data class for mapping responses with the actual data. The [data] is a MutableList of
 * [Match] data objects.
 */
data class GamesResponse(
    val data: MutableList<Match>
)
