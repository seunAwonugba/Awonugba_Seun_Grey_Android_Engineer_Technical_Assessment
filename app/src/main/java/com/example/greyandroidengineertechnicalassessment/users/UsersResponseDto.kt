package com.example.greyandroidengineertechnicalassessment.users

data class UsersResponseDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)