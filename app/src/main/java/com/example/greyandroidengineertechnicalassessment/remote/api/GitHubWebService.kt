package com.example.greyandroidengineertechnicalassessment.remote.api

import com.example.greyandroidengineertechnicalassessment.utils.Constants.REPO_SEARCH_END_POINT
import com.example.greyandroidengineertechnicalassessment.utils.Constants.USERS_SEARCH_END_POINT
import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubWebService {

    @GET(REPO_SEARCH_END_POINT)
    suspend fun searchRepositories(
        @Query("q") query : String,
        @Query("page") pageNumber : Int = 1
    ) : Response<RepositoryResponseDto>

    @GET(USERS_SEARCH_END_POINT)
    suspend fun searchUsers(
        @Query("q") query : String,
        @Query("page") pageNumber : Int = 1
    ) : Response<UsersResponseDto>
}