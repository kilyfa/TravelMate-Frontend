package com.example.travelmate.data.retrofit

import com.example.travelmate.data.model.RecommendationRequest
import com.example.travelmate.data.response.DetailResponse
import com.example.travelmate.data.response.RecommendationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("/home/recomendation")
    fun getRecommendations(
        @Body request: RecommendationRequest
    ): Call<RecommendationResponse>

    @GET("/home/{id}")
    fun getDetailPage (
        @Path("id") id: Int
    ): Call<DetailResponse>
}