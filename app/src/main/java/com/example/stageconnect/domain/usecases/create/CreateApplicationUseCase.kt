package com.example.stageconnect.domain.usecases.create

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class CreateApplicationUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun execute(applicationDto: ApplicationDto, fileUri: Uri?): ApplicationDto {
        // Serialize the UserDto to JSON string
        val objectMapper = ObjectMapper()
        val userDtoJson = objectMapper.writeValueAsString(applicationDto)

        // Correctly convert Uri to real File
        val file: File = if (fileUri != null){
            uriToFile(context, fileUri)
        }else{
            createEmptyFile()
        }
        return applicationRepository.createApplication(userDtoJson, file)
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".pdf", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        return tempFile
    }

    private fun createEmptyFile(): File {
        val emptyFile = File(context.cacheDir, "empty_file.pdf")
        if (!emptyFile.exists()) {
            emptyFile.createNewFile()
        }
        return emptyFile
    }
}