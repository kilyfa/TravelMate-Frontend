package com.example.travelmate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travelmate.R
import com.example.travelmate.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.submitButton.setOnClickListener {
            val city = binding.cityInput.text.toString().trim()
            val price = binding.priceInput.text.toString().toIntOrNull()
            val rating = binding.ratingInput.text.toString().toFloatOrNull()
            val category = binding.categoryInput.text.toString().trim()

            if (city.isEmpty() || price == null || rating == null || category.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save data to ViewModel
            searchViewModel.setSearchData(city, price, rating, category)

            // Navigate to ResultFragment
            findNavController().navigate(R.id.action_search_to_result)
        }

        return binding.root
    }
}
