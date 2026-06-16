package com.example.synhub.shared.application.dto

class SignUpRequest (
    val username: String,
    val name: String,
    val surname: String,
    val imgUrl: String,
    val email: String,
    val password: String,
    val roles: List<String> = listOf("ROLE_LEADER")
)