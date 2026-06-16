package com.example.synhub.invitations.application.dto

data class InvitationResponse(
    val id: Long,
    val member: InvitationMemberResponse
)

data class InvitationMemberResponse(
    val id: Long,
    val username: String,
    val name: String,
    val surname: String,
    val imgUrl: String,
)