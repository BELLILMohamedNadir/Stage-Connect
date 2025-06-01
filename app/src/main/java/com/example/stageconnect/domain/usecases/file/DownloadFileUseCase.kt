package com.example.stageconnect.domain.usecases.file

import com.example.stageconnect.data.remote.repository.FileRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class DownloadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun execute(fileName: String): Response<ResponseBody> {
        return fileRepository.downloadFile(fileName)
    }
}
