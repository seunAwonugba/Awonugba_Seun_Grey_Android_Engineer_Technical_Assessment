package com.example.greyandroidengineertechnicalassessment.view.data

data class UserDetailsResponse(
    val login : String,
    val name : String,
    val location : String,
    val url : String,
    val followers : Int,
    val following : Int
)