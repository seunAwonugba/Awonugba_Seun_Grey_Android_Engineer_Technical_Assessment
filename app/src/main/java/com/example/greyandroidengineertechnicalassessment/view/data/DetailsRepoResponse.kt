package com.example.greyandroidengineertechnicalassessment.view.data

data class DetailsRepoResponse(
    val id : String,
    val firstName : String,
    val secondName : String,
    val description : String,
    val forkedFrom : String,
    val update : String,
    val public : String,
    val star : String,
    val language : String,
    val fullName : String = ""
)
