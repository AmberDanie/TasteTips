package com.example.tastetips.data

import androidx.compose.ui.graphics.vector.ImageVector

data class RefrigeratorItem(
    val positionIcon: ImageVector,
    val positionName: String,
    val positionIndicator: Int,
    val positionExpirationDate: String
)

val refrigeratorItems: MutableList<RefrigeratorItem> = mutableListOf()