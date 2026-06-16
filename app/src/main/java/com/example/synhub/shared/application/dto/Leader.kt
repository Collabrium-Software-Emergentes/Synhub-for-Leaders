package com.example.synhub.shared.application.dto

data class Leader(
    val username: String,
    val name: String,
    val surname: String,
    val imgUrl: String,
    val email: String,
    val averageSolutionTime: String,
    val solvedRequests: Int
)