package com.example.greyandroidengineertechnicalassessment.remote.users

data class UsersResponseDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)