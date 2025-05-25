package com.example.stageconnect.domain

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {
    fun getFileNameAndSize(context: Context, uri: Uri): Pair<String, Long>? {
        // Check if URI is a file URI
        if (uri.scheme == "file") {
            val file = File(uri.path ?: return null)
            if (file.exists()) {
                return file.name to file.length()
            }
        }

        // Otherwise, handle content:// URIs
        val contentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            if (it.moveToFirst()) {
                val name = it.getString(nameIndex)
                val size = it.getLong(sizeIndex)
                return name to size
            }
        }
        return null
    }

    fun openPdf(context: Context, uri: Uri) {
        val fileUri = if (uri.scheme == "file") {
            val file = File(uri.path!!)
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } else {
            uri
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
        }
    }

    fun uriToFile(context: Context, uri: Uri?): File {
        if (uri != null){
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

            val fileExtension = context.contentResolver.getType(uri)?.let {
                when {
                    it.contains("image") -> ".jpg"
                    it.contains("pdf") -> ".pdf"
                    else -> ".unknown"
                }
            } ?: ".unknown"

            val tempFile = File.createTempFile("upload_", fileExtension, context.cacheDir)

            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            return tempFile
        }else{
            return createEmptyFile(context, ".pdf")
        }
    }

    private fun createEmptyFile(context: Context, extension: String = ".pdf"): File {
        val emptyFile = File(context.cacheDir, "empty_image$extension")
        if (!emptyFile.exists()) {
            emptyFile.createNewFile()
        }
        return emptyFile
    }

    fun savePdfToLocal(context: Context?, fileName: String?, inputStream: InputStream?): Uri? {
        // Validate all inputs
        if (context == null) {
            Log.e("FileSaveError", "Context is null")
            return null
        }
        if (fileName.isNullOrEmpty()) {
            Log.e("FileSaveError", "Invalid filename")
            return null
        }
        if (inputStream == null) {
            Log.e("FileSaveError", "Input stream is null")
            return null
        }

        // Sanitize filename
        val sanitizedFileName = fileName.replace("[^a-zA-Z0-9-_.]".toRegex(), "_")
        val file = File(context.cacheDir, "$sanitizedFileName.pdf")

        var outputStream: FileOutputStream? = null
        try {
            // Verify cache directory
            val cacheDir = context.cacheDir ?: run {
                Log.e("FileSaveError", "Cache directory is null")
                return null
            }

            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                Log.e("FileSaveError", "Failed to create cache directory")
                return null
            }

            outputStream = FileOutputStream(file)
            val buffer = ByteArray(8192)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            return Uri.fromFile(file)

        } catch (e: NullPointerException) {
            Log.e("FileSaveError", "Null pointer exception: ${e.stackTraceToString()}")
            return null
        } catch (e: IOException) {
            Log.e("FileSaveError", "IO error: ${e.stackTraceToString()}")
            file.delete() // Clean up partial file
            return null
        } catch (e: Exception) {
            Log.e("FileSaveError", "Unexpected error: ${e.stackTraceToString()}")
            return null
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                Log.e("FileSaveError", "Error closing input stream", e)
            }
            try {
                outputStream?.close()
            } catch (e: IOException) {
                Log.e("FileSaveError", "Error closing output stream", e)
            }
        }
    }



}

