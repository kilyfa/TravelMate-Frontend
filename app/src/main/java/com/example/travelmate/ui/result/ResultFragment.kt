package com.example.travelmate.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmate.R
import com.example.travelmate.data.response.TravelResponseItem
import com.example.travelmate.ui.adapter.DestinationAdapter

class ResultFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DestinationAdapter
    private val destinations = mutableListOf<TravelResponseItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.rv_result)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inisialisasi Adapter
        adapter = DestinationAdapter(destinations)
        recyclerView.adapter = adapter

        // Ambil data dari argument
        val resultData = arguments?.getParcelableArrayList<TravelResponseItem>("destinations")
        if (resultData != null) {
            destinations.clear()
            destinations.addAll(resultData)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "Tidak ada data untuk ditampilkan", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}