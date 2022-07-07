package com.example.greyandroidengineertechnicalassessment.remote.repository

data class RepositoryResponseDto(
    val incomplete_results: Boolean? = false,
    val items: List<Item>? = emptyList(),
    val total_count: Int? = 0
)