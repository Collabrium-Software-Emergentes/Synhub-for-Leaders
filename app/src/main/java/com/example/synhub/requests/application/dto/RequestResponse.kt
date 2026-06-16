package com.example.synhub.requests.application.dto

import com.example.synhub.tasks.application.dto.TaskResponse

data class RequestResponse(
    val id: Long,
    val description: String,
    val requestType: String,
    val requestStatus: String,
    val task: TaskResponse,
    val createdAt: String,
    val updatedAt: String
)