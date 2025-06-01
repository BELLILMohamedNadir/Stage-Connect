package com.example.stageconnect.domain.usecases.auth

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.AuthDto
import com.example.stageconnect.data.remote.repository.RegisterRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun execute(authDto: AuthDto, imageUri: Uri?): String {
        // Serialize the UserDto to JSON string
        val objectMapper = ObjectMapper()
        val authDtoJson = objectMapper.writeValueAsString(authDto)

        // Correctly convert Uri to real File
        val file: File = if (imageUri != null){
            uriToFile(context, imageUri)
        }else{
            createEmptyImageFile()
        }


        // Call the repository to register the user
        return registerRepository.registerUser(authDtoJson, file)
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        return tempFile
    }

    private fun createEmptyImageFile(): File {
        val emptyFile = File(context.cacheDir, "empty_image.jpg")
        if (!emptyFile.exists()) {
            emptyFile.createNewFile()
        }
        return emptyFile
    }
}
