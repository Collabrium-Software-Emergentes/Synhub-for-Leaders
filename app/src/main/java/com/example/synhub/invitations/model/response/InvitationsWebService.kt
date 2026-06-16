package com.example.synhub.invitations.model.response

import com.example.synhub.invitations.application.dto.InvitationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface InvitationsWebService {
    @GET("groups/api/v1/invitations/group")
    suspend fun getGroupInvitations(): List<InvitationResponse>

    @PATCH("groups/api/v1/group/invitations/{invitationId}")
    suspend fun processInvitation(
        @Path("invitationId") invitationId: Long,
        @Query("accept") accept: Boolean
    ): Response<Void>
}