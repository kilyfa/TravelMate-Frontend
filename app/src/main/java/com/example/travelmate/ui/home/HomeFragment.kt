package com.example.travelmate.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelmate.api.ApiClient
import com.example.travelmate.api.HomeApiService
import com.example.travelmate.api.PlaceResponse
import com.example.travelmate.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView
        setupRecyclerView()

        // Fetch data dari API
        fetchHomeData()

        return binding.root
    }

    private fun setupRecyclerView() {
        // Mengatur layout manager dan adapter awal
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = PlaceAdapter(emptyList())
    }

    private fun fetchHomeData() {
        val apiService = ApiClient.retrofit.create(HomeApiService::class.java)

        apiService.getPlaces().enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.isSuccessful) {
                    val placesResponse = response.body()
                    if (placesResponse != null && placesResponse.data.isNotEmpty()) {
                        // Ambil 10 tempat secara acak
                        val randomPlaces = placesResponse.data.shuffled().take(10)

                        // Update RecyclerView dengan data baru
                        binding.recyclerView.adapter = PlaceAdapter(randomPlaces)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No places available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load data: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("HomeFragment", "Error fetching data: ${t.localizedMessage}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
