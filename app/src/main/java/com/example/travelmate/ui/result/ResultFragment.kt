package com.example.travelmate.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelmate.databinding.FragmentResultBinding
import com.example.travelmate.ui.adapter.ResultAdapter
import com.example.travelmate.data.model.RecommendationRequest
import com.example.travelmate.data.repository.RecommendationRepository
import com.example.travelmate.data.retrofit.ApiConfig

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var adapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val apiService = ApiConfig.getApiService()
        val repository = RecommendationRepository(apiService)
        resultViewModel = ViewModelProvider(
            this,
            ResultViewModelFactory(repository)
        )[ResultViewModel::class.java]

        // Inisialisasi adapter
        adapter = ResultAdapter { item ->
            Toast.makeText(requireContext(), "Selected: ${item.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ResultFragment.adapter
        }

        // Ambil data yang dipassing melalui ViewModel
        val city = arguments?.getString("city") ?: ""
        val price = arguments?.getString("price")?.toInt() ?: 0
        val rating = arguments?.getString("rating")?.toFloat() ?: 0f
        val category = arguments?.getString("category") ?: ""

        val request = RecommendationRequest(city, price, rating, category)
        resultViewModel.fetchRecommendations(request)

        // Observasi perubahan data
        resultViewModel.recommendations.observe(viewLifecycleOwner) { recommendations ->
            adapter.submitList(recommendations)
        }

        // Observasi error
        resultViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
