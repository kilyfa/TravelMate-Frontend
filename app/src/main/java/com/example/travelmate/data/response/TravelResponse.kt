package com.example.travelmate.data.response

import com.google.gson.annotations.SerializedName

data class TravelResponse(

	@field:SerializedName("TravelResponse")
	val travelResponse: List<TravelResponseItem>
)

data class TravelResponseItem(

	@field:SerializedName("Description")
	val description: String,

	@field:SerializedName("Place_Id")
	val placeId: Int,

	@field:SerializedName("Place_Name")
	val placeName: String,

	@field:SerializedName("Price")
	val price: Int,

	@field:SerializedName("Rating")
	val rating: Any,

	@field:SerializedName("Alamat Detail")
	val alamatDetail: String,

	@field:SerializedName("City")
	val city: String
)
