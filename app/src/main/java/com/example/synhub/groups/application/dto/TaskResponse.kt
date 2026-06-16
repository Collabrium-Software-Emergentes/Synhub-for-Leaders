package com.example.synhub.groups.application.dto

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: String,
    val createdAt: String,
    val status: String
)

