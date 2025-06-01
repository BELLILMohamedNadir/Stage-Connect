package com.example.stageconnect.domain.model

data class NavigationItem(
    val label: String,
    val icon: Int,
    val onClick: () -> Unit,
    val onCardClick: () -> Unit
)
