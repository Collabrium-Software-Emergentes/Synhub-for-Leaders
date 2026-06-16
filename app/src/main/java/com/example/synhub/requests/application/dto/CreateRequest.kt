package com.example.synhub.requests.application.dto

data class CreateRequest(
    val description: String,
    val requestType: String,
    val taskId: Long
)