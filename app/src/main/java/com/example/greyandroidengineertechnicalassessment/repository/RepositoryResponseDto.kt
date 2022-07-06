package com.example.greyandroidengineertechnicalassessment.repository

data class RepositoryResponseDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)