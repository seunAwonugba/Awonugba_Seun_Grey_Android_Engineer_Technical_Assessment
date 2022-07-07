package com.example.greyandroidengineertechnicalassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greyandroidengineertechnicalassessment.remote.repository.usersrepo.UsersRepositoryResponseDto
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    private val _state : MutableLiveData<Resource<UsersRepositoryResponseDto>> = MutableLiveData()
    var state : LiveData<Resource<UsersRepositoryResponseDto>> = _state



    fun getUsersRepo(userName : String) = viewModelScope.launch {
        _state.postValue(Resource.Loading())
        val response = mainRepository.getUserRepository(userName = userName)
        _state.postValue(handleUserRepoResponse(response))
    }

    private fun handleUserRepoResponse(response : Response<UsersRepositoryResponseDto>) : Resource<UsersRepositoryResponseDto>{
        if (response.isSuccessful){
            response.body()?.let { data->
                return Resource.Success(data)
            }
        }
        return Resource.Error(response.message())
    }
}