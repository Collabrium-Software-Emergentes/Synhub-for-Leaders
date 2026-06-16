package com.example.synhub.shared.model.response

import com.example.synhub.shared.application.dto.SignInRequest
import com.example.synhub.shared.application.dto.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInWebService {
    @POST("iam/api/v1/authentication/sign-in")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>
}