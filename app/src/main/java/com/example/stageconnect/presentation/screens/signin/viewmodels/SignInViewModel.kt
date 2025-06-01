package com.example.stageconnect.presentation.screens.signin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.domain.CONSTANT.JWT_TOKEN
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.AuthenticateUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _authenticationResult = MutableLiveData<Result<AuthenticationResponse>>()
    val authenticationResult: LiveData<Result<AuthenticationResponse>> get() = _authenticationResult


    fun authenticate(request: AuthenticationRequest) {
        viewModelScope.launch {
            _authenticationResult.postValue(Result.Loading())

            try {
                val token = try {
                    getFcmToken()
                } catch (e: Exception) {
                    Log.w("FCM", "Fetching FCM token failed: ${e.message}")
                    null
                }

                request.deviceToken = token

                val result = authenticateUseCase.execute(request)

                storageRepository.save(data = result.token, label = JWT_TOKEN)
                storageRepository.save(data = "${result.id}", label = USER_ID)
                storageRepository.save(data = result.role, label = USER_ROLE)

                _authenticationResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _authenticationResult.postValue(Result.Error(e))
            }
        }
    }

    fun saveInfos(email: String, password: String, checked: Boolean){
        storageRepository.save(label = CONSTANT.EMAIL, data = email)
        storageRepository.save(label = CONSTANT.PASSWORD, data = password)
        storageRepository.saveBoolean(label = CONSTANT.CHECKED, data = checked)
    }

    fun saveOnBoarding(onBoarding: Boolean){
        storageRepository.saveBoolean(label = CONSTANT.ONBOARDING, data = onBoarding)
    }


    fun getInfo(label: String): String{
        return storageRepository.get(label = label) ?: ""
    }

    fun checked(label: String): Boolean{
        return storageRepository.getBoolean(label = label)
    }

    private suspend fun getFcmToken(): String = suspendCancellableCoroutine { cont ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cont.resume(task.result, null)
                } else {
                    cont.resumeWithException(task.exception ?: Exception("Unknown error"))
                }
            }
    }


}
