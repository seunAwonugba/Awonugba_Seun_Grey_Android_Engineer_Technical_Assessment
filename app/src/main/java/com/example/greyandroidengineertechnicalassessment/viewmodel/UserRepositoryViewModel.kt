package com.example.greyandroidengineertechnicalassessment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

}