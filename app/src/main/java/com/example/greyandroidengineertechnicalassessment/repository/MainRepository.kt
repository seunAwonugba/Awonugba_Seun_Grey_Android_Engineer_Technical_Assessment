package com.example.greyandroidengineertechnicalassessment.repository

import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.repository.usersrepo.UsersRepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.userdetails.UserDetailsResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import com.example.greyandroidengineertechnicalassessment.view.data.UsersResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MainRepository {
    suspend fun searchRepository(query : String, pageNumber : Int) : Response<RepositoryResponseDto>
    suspend fun searchUsers(query : String, pageNumber : Int) : Response<UsersResponseDto>
    suspend fun getUser(userName : String) : Response<UserDetailsResponseDto>
    suspend fun getUserRepository(userName : String) : Response<UsersRepositoryResponseDto>
}