package com.example.greyandroidengineertechnicalassessment.repository

import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import retrofit2.Response

interface MainRepository {
    suspend fun searchRepository(query : String, pageNumber : Int) : Response<RepositoryResponseDto>
    suspend fun searchUsers(query : String, pageNumber : Int) : Response<UsersResponseDto>
}