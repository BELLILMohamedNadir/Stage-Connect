package com.example.stageconnect.domain.usecases.update

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class UpdateRecruiterUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun execute(request: RecruiterDto, fileUri: Uri?): RecruiterDto {
        val objectMapper = ObjectMapper()
        val recruiterDtoJson = objectMapper.writeValueAsString(request)
        return studentRepository.updateRecruiter(recruiterDto = recruiterDtoJson, file = uriToFile(context, fileUri))
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
