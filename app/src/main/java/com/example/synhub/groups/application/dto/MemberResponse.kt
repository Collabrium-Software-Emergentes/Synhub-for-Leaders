package com.example.synhub.groups.application.dto

data class MemberResponse(
    val id: Long,
    val username: String,
    val name: String,
    val surname: String,
    val imgUrl: String
)
