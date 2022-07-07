package com.example.greyandroidengineertechnicalassessment.repository

import com.example.greyandroidengineertechnicalassessment.remote.api.GitHubWebService
import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.repository.usersrepo.UsersRepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.userdetails.UserDetailsResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import com.example.greyandroidengineertechnicalassessment.view.data.UsersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        return gitHubWebService.searchUsers(
            query = query,
            pageNumber = pageNumber
        )
    }

    override suspend fun getUser(userName: String): Response<UserDetailsResponseDto> {
        return gitHubWebService.getUser(userName = userName)
    }

    override suspend fun getUserRepository(userName: String): Response<UsersRepositoryResponseDto> {
        return gitHubWebService.getUsersRepository(userName)
    }


}