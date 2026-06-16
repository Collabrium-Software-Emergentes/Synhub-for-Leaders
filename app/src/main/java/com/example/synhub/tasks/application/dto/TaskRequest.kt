package com.example.synhub.tasks.application.dto

data class TaskRequest(
    val title: String,
    val description: String,
    val dueDate: String,
)