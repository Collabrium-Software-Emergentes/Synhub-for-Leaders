package com.example.synhub.analytics.application.dto

data class AnalyticsResponse(
    val type: String?,
    val value: Double?,
    val details: Map<String, Any>?,
    val rescheduledMemberIds: List<Long>? = null
)
