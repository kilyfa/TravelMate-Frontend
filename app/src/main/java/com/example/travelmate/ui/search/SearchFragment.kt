package com.example.travelmate.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmate.R
import com.example.travelmate.data.model.SearchRequest
import com.example.travelmate.ui.adapter.DestinationAdapter

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var edtPrice: EditText
    private lateinit var edtRating: EditText
    private lateinit var edtCity: EditText
    private lateinit var edtCategory: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DestinationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtPrice = view.findViewById(R.id.editTextPrice)
        edtRating = view.findViewById(R.id.editTextRating)
        edtCity = view.findViewById(R.id.editTextCity)
        edtCategory = view.findViewById(R.id.editTextCategory)
        btnSearch = view.findViewById(R.id.buttonSubmit)
        recyclerView = view.findViewById(R.id.rv_result)

        // Adapter RecyclerView
        adapter = DestinationAdapter()
        recyclerView.adapter = adapter

        // Inisialisasi ViewModel
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // Observasi destinasi
        searchViewModel.destinations.observe(viewLifecycleOwner, Observer { destinations ->
            adapter.submitList(destinations)
        })

        // Klik tombol untuk mencari
        btnSearch.setOnClickListener {
            val price = edtPrice.text.toString().toInt()
            val rating = edtRating.text.toString().toFloat()
            val city = edtCity.text.toString()
            val category = edtCategory.text.toString()

            val request = SearchRequest(price, rating, city, category)
            searchViewModel.fetchDestinations(request)
        }
    }
}