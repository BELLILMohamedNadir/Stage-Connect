package com.example.stageconnect.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.domain.result.Result

@Composable
fun <T> ObserveResult(
    result: Result<T>?,
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: @Composable (String) -> Unit = {}
) {
    when (result) {
        is Result.Loading -> onLoading()
        is Result.Success -> onSuccess(result.data)
        is Result.Error -> onError(result.exception.message ?: "An unexpected error occurred")
        null -> Unit // Do nothing when result is null
    }
}

