package com.example.travelmate.data.retrofit

import com.example.travelmate.data.model.RecommendationRequest
import com.example.travelmate.data.response.RecommendationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/home/recomendation")
    fun getRecommendations(
        @Body request: RecommendationRequest
    ): Call<RecommendationResponse>
}