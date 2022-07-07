package com.example.greyandroidengineertechnicalassessment.repository

import com.example.greyandroidengineertechnicalassessment.remote.api.GitHubWebService
import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val gitHubWebService: GitHubWebService
) : MainRepository {
    override suspend fun  searchRepository(
        query: String,
        pageNumber: Int
    ): Response<RepositoryResponseDto> {
        return gitHubWebService.searchRepositories(
            query = query,
            pageNumber = pageNumber
        )
    }

    override suspend fun searchUsers(query: String, pageNumber: Int): Response<UsersResponseDto> {
        TODO("Not yet implemented")
    }


}