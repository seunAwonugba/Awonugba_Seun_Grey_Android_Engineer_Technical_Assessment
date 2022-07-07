package com.example.greyandroidengineertechnicalassessment.viewmodel

import com.example.greyandroidengineertechnicalassessment.view.data.UserDetailsResponse


data class UserDetailsState(
    val isLoading: Boolean = false,
    val userDetailsResponse : UserDetailsResponse? = null,
    val error : String = ""
)