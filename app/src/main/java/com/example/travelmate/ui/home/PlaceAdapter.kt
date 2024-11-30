package com.example.travelmate.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmate.R

data class PlaceResponse(
    val data: List<Place>
)

data class Place(
    val address: String,
    val category: String,
    val city: String,
    val id: Int,
    val name: String,
    val price: String,
    val rating: Int
)


class PlaceAdapter(private val places: List<com.example.travelmate.api.Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewAddress: TextView = view.findViewById(R.id.textViewAddress)
        val textViewCategory: TextView = view.findViewById(R.id.textViewCategory)
        val textViewCity: TextView = view.findViewById(R.id.textViewCity)
        val textViewRating: TextView = view.findViewById(R.id.textViewRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.textViewName.text = place.name
        holder.textViewAddress.text = place.address
        holder.textViewCategory.text = place.category
        holder.textViewCity.text = place.city
        holder.textViewRating.text = "Rating: ${place.rating}"
    }

    override fun getItemCount() = places.size
}