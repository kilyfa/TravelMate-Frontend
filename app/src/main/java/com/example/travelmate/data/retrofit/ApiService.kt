package com.example.travelmate.data.retrofit

import com.example.travelmate.data.model.SearchRequest
import com.example.travelmate.data.response.TravelResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("search")
    fun searchDestinations(@Body request: SearchRequest): Call<List<TravelResponseItem>>
}