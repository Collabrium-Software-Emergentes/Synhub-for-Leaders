package com.example.synhub.shared.application.dto

data class SignInResponse(
    val id: Long,
    val username: String,
    val token: String
)