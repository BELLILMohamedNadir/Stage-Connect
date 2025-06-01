package com.example.stageconnect.domain.usecases.user

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class UpdateStudentUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun execute(request: StudentDto, fileUri: Uri?): StudentDto {
        val objectMapper = ObjectMapper()
        val studentDtoJson = objectMapper.writeValueAsString(request)
        return studentRepository.updateStudent(studentDto = studentDtoJson, file = uriToFile(context, fileUri))
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
