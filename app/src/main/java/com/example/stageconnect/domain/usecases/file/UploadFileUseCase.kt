package com.example.stageconnect.domain.usecases.file

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.remote.repository.FileRepository
import com.example.stageconnect.domain.FileUtils.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject


class UploadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun execute(userId: Long, fileUri: Uri?, uploadPhoto: Boolean): String {
        val file: File = uriToFile(context, fileUri)
        return fileRepository.uploadFile(userId, file, uploadPhoto)
    }

}

