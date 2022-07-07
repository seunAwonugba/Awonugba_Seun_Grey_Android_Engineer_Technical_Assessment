package com.example.greyandroidengineertechnicalassessment.remote.users

data class UsersResponseDto(
    val incomplete_results: Boolean,
    val items: MutableList<Item> = mutableListOf(),
    val total_count: Int
)