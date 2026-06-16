package com.example.synhub.tasks.application.dto

import android.service.quicksettings.Tile

data class EditTaskRequest(
    val title: String,
    val description: String,
    val dueDate: String,
    val memberId: Long
)