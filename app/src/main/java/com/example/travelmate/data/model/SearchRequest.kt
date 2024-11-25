package com.example.travelmate.data.model

data class SearchRequest(
    val price: Int,
    val rating: Float,
    val city: String,
    val category: String
)
