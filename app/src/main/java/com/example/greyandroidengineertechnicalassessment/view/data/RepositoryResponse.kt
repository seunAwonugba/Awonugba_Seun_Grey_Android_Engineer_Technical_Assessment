package com.example.greyandroidengineertechnicalassessment.view.data

data class RepositoryResponse(
    val id : Int,
    val fullName : String,
    val profilePicture : String,
    val star : Int,
    val language : String,
    val description : String,
    val topics : List<String>,

)
