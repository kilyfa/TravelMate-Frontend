package com.example.travelmate.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelmate.data.model.SearchRequest
import com.example.travelmate.data.response.TravelResponseItem
import com.example.travelmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _destinations = MutableLiveData<List<TravelResponseItem>>()
    val destinations: LiveData<List<TravelResponseItem>> = _destinations

    // Fungsi untuk melakukan POST request ke API backend
    fun fetchDestinations(request: SearchRequest) {
        ApiConfig.instance.searchDestinations(request).enqueue(object : Callback<List<TravelResponseItem>> {
            override fun onResponse(
                call: Call<List<TravelResponseItem>>,
                response: Response<List<TravelResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _destinations.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<TravelResponseItem>>, t: Throwable) {
                _destinations.value = emptyList()
            }
        })
    }
}