package com.example.travelmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.travelmate.R
import com.example.travelmate.api.ApiClient
import com.example.travelmate.data.response.DetailResponse
import com.example.travelmate.data.response.Main
import com.example.travelmate.data.response.WeatherItem
import com.example.travelmate.data.retrofit.ApiService
import com.example.travelmate.databinding.FragmentDetailBinding
import com.example.travelmate.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        // Initialize Retrofit API service
        apiService = ApiClient.retrofit.create(ApiService::class.java)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val placeId = args.placeId

        if (placeId != -1) {
            // Fetch the details of the place using the placeId
            fetchPlaceDetails(placeId)
        }

        binding.backButton.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailToHome()
            findNavController().navigate(action)
        }

        binding.postButton.setOnClickListener {

        }

        return binding.root
    }

    private fun fetchPlaceDetails(placeId: Int) {
        apiService.getDetailPage(placeId).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val placeDetails = response.body()

                    if (placeDetails != null) {
                        // Update the UI with the fetched details
                        binding.nameTextView.text = placeDetails.name
                        binding.cityTextView.text = placeDetails.city
                        binding.priceTextView.text = placeDetails.price
                        binding.ratingTextView.text = placeDetails.rating.toString()
                        binding.categoryTextView.text = placeDetails.category
                        binding.addressTextView.text = placeDetails.address

                        updateWeather(placeDetails.weatherResponse.weather, placeDetails.weatherResponse.main)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun covertToCelcius(temp: Float): String {
        val celsius = temp - 273.15
        return String.format("%.2f", celsius)
    }

    private fun updateWeather(
        weatherList: List<WeatherItem>,
        weaterTemp: Main
    ) {
        // Assuming you want to display the weather information in the UI
        if (weatherList.isNotEmpty()) {
            val weather = weatherList[0]
            val temp = covertToCelcius(weaterTemp.temp)
            val feelTemp = covertToCelcius(weaterTemp.feelsLike)
            binding.weatherDescriptionTextView.text = weather.description
            binding.tempTextView.text = "Tempratur: $temp°C"
            binding.feelTempTextView.text = "Feel like: $feelTemp°C"

            val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"

            Glide.with(requireContext())
                .load(iconUrl)
                .into(binding.weatherIconImageView)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
