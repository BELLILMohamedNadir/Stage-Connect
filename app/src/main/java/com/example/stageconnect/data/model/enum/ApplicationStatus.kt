package com.example.stageconnect.data.model.enum

import android.hardware.biometrics.BiometricManager.Strings
import androidx.compose.ui.res.stringResource

enum class ApplicationStatus(val label: String) {
    ACCEPTED("Application Accepted"),
    SENT("Application Sent"),
    REJECTED("Application Rejected")
}