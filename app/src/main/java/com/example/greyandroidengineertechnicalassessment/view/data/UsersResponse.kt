package com.example.greyandroidengineertechnicalassessment.view.data

import java.io.Serializable


data class UsersResponse(
    val id: Int,
    val login : String,
    val loginTwo : String,
    val profilePicture : String,
) : Serializable
