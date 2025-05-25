package com.example.stageconnect.domain.model

import com.example.stageconnect.domain.model.enums.STATUS

data class Application(
    val offer: Offer,
    var status: STATUS
)
