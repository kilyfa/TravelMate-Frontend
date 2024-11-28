package com.example.travelmate.api

data class PlaceResponse(
    val data: List<Place>
)

data class Place(
    val address: String,
    val category: String,
    val city: String,
    val id: String,
    val name: String,
    val price: String,
    val rating: Double
)
