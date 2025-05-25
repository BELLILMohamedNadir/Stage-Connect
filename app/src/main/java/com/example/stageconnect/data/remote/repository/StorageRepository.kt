package com.example.stageconnect.data.remote.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val encryptedPrefs: SharedPreferences
) {

    fun save(data: String, label: String) {
        encryptedPrefs.edit() { putString(label, data) }
    }

    fun get(label: String): String? {
        return encryptedPrefs.getString(label, null)
    }
}
