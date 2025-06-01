package com.example.stageconnect.domain.usecases.user

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.EstablishmentDto
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class UpdateEstablishmentUseCase @Inject constructor(
    private val establishmentRepository: EstablishmentRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun execute(request: EstablishmentDto, fileUri: Uri?): EstablishmentDto {
        val objectMapper = ObjectMapper()
        val establishmentDtoJson = objectMapper.writeValueAsString(request)
        return establishmentRepository.updateEstablishment(establishmentDto = establishmentDtoJson, file = uriToFile(context, fileUri))
    }

    private fun uriToFile(context: Context, uri: Uri?): File {
       if (uri != null){
           val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
           val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
           tempFile.outputStream().use { outputStream ->
               inputStream?.copyTo(outputStream)
           }
           return tempFile
       }else{
           return createEmptyImageFile()
       }
    }

    private fun createEmptyImageFile(): File {
        val emptyFile = File(context.cacheDir, "empty_image.jpg")
        if (!emptyFile.exists()) {
            emptyFile.createNewFile()
        }
        return emptyFile
    }
}
