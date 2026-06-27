package com.example.synhub.groups.model.response

import com.example.synhub.groups.application.dto.GroupMember
import com.example.synhub.groups.application.dto.GroupRequest
import com.example.synhub.groups.application.dto.GroupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface GroupWebService {
    @GET("groups/api/v1/leader/group")
    suspend fun getLeaderGroups(): Response<GroupResponse>

    @GET("groups/api/v1/groups/members")
    suspend fun getGroupMembers(): Response<List<GroupMember>>

    @POST("groups/api/v1/leader/group")
    suspend fun createGroup(@Body groupRequest: GroupRequest): Response<GroupResponse>

    @Multipart
    @POST("groups/api/v1/leader/group")
    suspend fun createGroupWithImage(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<GroupResponse>

    @DELETE("groups/api/v1/leader/group/members/{memberId}")
    suspend fun deleteGroupMember(@Path("memberId") memberId: Long): Response<Void>
}
