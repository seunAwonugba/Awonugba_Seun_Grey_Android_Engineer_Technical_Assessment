package com.example.greyandroidengineertechnicalassessment.usecase

import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import com.example.greyandroidengineertechnicalassessment.view.data.UserDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//class UserDetailsUseCase @Inject constructor(
//    private val mainRepository: MainRepository
//) {
//
//    operator fun invoke(userName : String) : Flow<Resource<UserDetailsResponse>> = flow {
//        try {
//            emit(Resource.Loading())
//
//            val response = mainRepository.getUser(userName = userName)
//            val data = response.body()
//
//            if (response.isSuccessful && data!=null){
//                emit(Resource.Success(
//                    data = UserDetailsResponse(
//                        login = data.login ?: "",
//                        name = data.name ?: "",
//                        location = data.location ?: "N/A",
//                        url = data.html_url ?: "",
//                        followers = data.followers ?: 0,
//                        following = data.following ?: 0
//                    )
//                ))
//            }else{
//                emit(Resource.Error("A ${response.code()} error occurred: caused by : ${response.message()}"))
//            }
//        }catch (err : HttpException){
//            emit(Resource.Error("A ${err.code()} error occurred: caused by : ${err.message()}"))
//        }catch (err : IOException){
//            emit(Resource.Error("An error occurred: caused by ${err.message}"))
//        }
//    }
//}