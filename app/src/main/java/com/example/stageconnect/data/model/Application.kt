package com.example.stageconnect.data.model

import com.example.stageconnect.data.model.enum.ApplicationStatus

data class Application(
    val offer: Offer,
    var status: ApplicationStatus
)
