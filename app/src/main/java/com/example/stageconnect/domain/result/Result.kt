package com.example.stageconnect.domain.result

sealed class Result<out T> {

    data class Loading<out T>(val data: T? = null) : Result<T>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    // Helper function to check if the result is a success
    fun isSuccess(): Boolean = this is Success

    // Helper function to check if the result is an error
    fun isError(): Boolean = this is Error
}
