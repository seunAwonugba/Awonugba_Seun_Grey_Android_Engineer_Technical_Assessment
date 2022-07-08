package com.example.greyandroidengineertechnicalassessment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greyandroidengineertechnicalassessment.remote.userdetails.UserDetailsResponseDto
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

//@HiltViewModel
//class UserViewModel @Inject constructor(
//    private var mainRepository: MainRepository
//) : ViewModel() {
//
//    var state : MutableLiveData<Response<UserDetailsResponseDto>> = MutableLiveData()
//
//    fun getUserDetails(userName : String){
//        viewModelScope.launch {
//            val response = mainRepository.getUser(userName = userName)
//            state.value = response
//        }
//    }
//
//}