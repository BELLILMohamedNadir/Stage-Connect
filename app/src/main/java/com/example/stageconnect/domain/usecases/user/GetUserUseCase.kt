package com.example.stageconnect.domain.usecases.user

import com.example.stageconnect.data.remote.repository.RegisterRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val registerRepository: RegisterRepository) {

//    suspend fun execute(userId: Int): User {
//        return userRepository.getUser(userId)
//    }
}