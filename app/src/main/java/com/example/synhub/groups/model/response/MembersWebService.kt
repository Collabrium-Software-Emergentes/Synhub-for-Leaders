package com.example.synhub.groups.model.response

import com.example.synhub.groups.application.dto.MemberResponse
import com.example.synhub.groups.application.dto.TaskResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MembersWebService {
    @GET("groups/api/v1/groups/members")
    suspend fun getGroupMembers(): Response<List<MemberResponse>>

    @GET("tasks/api/v1/members/{memberId}/tasks/next")
    suspend fun getNextTaskForMember(@Path("memberId") memberId: Long): Response<TaskResponse>

    @GET("tasks/api/v1/member/details/{memberId}")
    suspend fun getMemberDetails(@Path("memberId") memberId: Long): Response<MemberResponse>

    @GET("tasks/api/v1/members/{memberId}/tasks")
    suspend fun getMemberTasks(@Path("memberId") memberId: Long): Response<List<TaskResponse>>
}
