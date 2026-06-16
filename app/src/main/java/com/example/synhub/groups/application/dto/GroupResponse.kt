package com.example.synhub.groups.application.dto

data class GroupResponse(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val description: String,
    val code: String,
    val memberCount: Int
)

