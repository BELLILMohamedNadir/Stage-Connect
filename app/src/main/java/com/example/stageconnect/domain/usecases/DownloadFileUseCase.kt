package com.example.stageconnect.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.remote.repository.FileRepository
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.domain.FileUtils.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class DownloadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun execute(fileName: String): Response<ResponseBody> {
        return fileRepository.downloadFile(fileName)
    }
}
