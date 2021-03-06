package com.example.greyandroidengineertechnicalassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    private val _state : MutableLiveData<Resource<RepositoryResponseDto>> = MutableLiveData()
    var state : LiveData<Resource<RepositoryResponseDto>> = _state

    var pageNumber = 1

    var searchedRepoResponse : RepositoryResponseDto? = null




    fun searchGitHubRepository(query : String) = viewModelScope.launch {
        _state.postValue(Resource.Loading())
        val response = mainRepository.searchRepository(query = query, pageNumber = pageNumber)
        _state.postValue(handleSearchRepoResponse(response = response))
    }

    private fun handleSearchRepoResponse(response : Response<RepositoryResponseDto>) : Resource<RepositoryResponseDto> {
        if (response.isSuccessful){
            response.body()?.let {
                //increase page number if response is successful
                pageNumber++

                if (searchedRepoResponse == null){
                    searchedRepoResponse = it
                }else{
                    val oldData =searchedRepoResponse?.items
                    val newData = it.items
                    if (newData != null) {
                        oldData?.addAll(newData)
                    }
                }


                return Resource.Success(searchedRepoResponse ?: it)
            }
        }else if (response.code() == 404){
            return Resource.Error("Repository not found")
        }else if (response.code() == 500){
            return Resource.Error("Internal server error")
        }
        return Resource.Error(response.message())
    }
}