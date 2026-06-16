package com.example.synhub.tasks.application.dto

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val status: String,
    val member: TaskMember,
    val groupId: Long
)

data class TaskMember(
    val id: Long,
    val name: String,
    val surname: String,
    val urlImage: String
)