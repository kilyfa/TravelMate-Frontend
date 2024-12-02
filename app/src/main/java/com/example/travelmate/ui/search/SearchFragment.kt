package com.example.travelmate.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelmate.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val categories = listOf("Edukasi", "Wisata Alam", "Kuliner", "Sejarah", "Edukasi dan Hiburan", "Taman Kota", "Pantai")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        binding.categoryInput.setAdapter(categoryAdapter)


        binding.submitButton.setOnClickListener {
            val city = binding.cityInput.text.toString().trim()
            val price = binding.priceInput.text.toString().toIntOrNull()
            val rating = binding.ratingInput.text.toString().toFloatOrNull()
            val category = binding.categoryInput.text.toString().trim()

            // Validasi input
            if (city.isEmpty() || price == null || rating == null || category.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Menggunakan Safe Args untuk mengirim data ke ResultFragment
            val action = SearchFragmentDirections.actionSearchToResult(city, price, rating, category)
            findNavController().navigate(action)
        }

        return binding.root
    }
}
