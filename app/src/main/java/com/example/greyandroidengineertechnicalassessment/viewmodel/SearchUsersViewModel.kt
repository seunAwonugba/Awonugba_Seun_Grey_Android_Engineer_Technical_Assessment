package com.example.greyandroidengineertechnicalassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greyandroidengineertechnicalassessment.remote.repository.RepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.remote.users.UsersResponseDto
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    private val _state : MutableLiveData<Resource<UsersResponseDto>> = MutableLiveData()
    var state : LiveData<Resource<UsersResponseDto>> = _state

    var pageNumber = 1

    var searchedUsersResponse : UsersResponseDto? = null


    init {
        initialStateRes()
        searchUsers("seun")
    }

    fun searchUsers(query : String) = viewModelScope.launch {
        _state.postValue(Resource.Loading())
        val response = mainRepository.searchUsers(query, pageNumber)
        _state.postValue(handleSearchUsersResponse(response))
    }

    private fun initialStateRes() = viewModelScope.launch {
        _state.postValue(Resource.Initial())
    }



    private fun handleSearchUsersResponse(response : Response<UsersResponseDto>) : Resource<UsersResponseDto> {
        if (response.isSuccessful){
            response.body()?.let {
                //increase page number if response is successful
                pageNumber++

                if (searchedUsersResponse == null){
                    searchedUsersResponse = it
                }else{
                    val oldData =searchedUsersResponse?.items
                    val newData = it.items
                    oldData?.addAll(newData)
                }


                return Resource.Success(searchedUsersResponse ?: it)
            }
        }else if (response.code() == 404){
            return Resource.Error("User not found")
        }else if (response.code() == 500){
            return Resource.Error("Internal server error")
        }
        return Resource.Error(response.message())
    }

}