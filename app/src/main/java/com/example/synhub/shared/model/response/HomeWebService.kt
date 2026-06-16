package com.example.synhub.shared.model.response

import com.example.synhub.shared.application.dto.Leader
import retrofit2.Response
import retrofit2.http.GET

interface HomeWebService {
    @GET("groups/api/v1/leader/details")
    suspend fun getLeaderDetails(): Response<Leader>
}

