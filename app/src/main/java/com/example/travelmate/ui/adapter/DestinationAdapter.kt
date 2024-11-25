package com.example.travelmate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmate.R
import com.example.travelmate.data.response.TravelResponseItem
import com.example.travelmate.databinding.ItemDestinationBinding

class DestinationAdapter : ListAdapter<TravelResponseItem, DestinationAdapter.DestinationViewHolder>(DestinationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = getItem(position)
        holder.bind(destination)
    }

    class DestinationViewHolder(private val binding: ItemDestinationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(destination: TravelResponseItem) {
            binding.textName.text = destination.placeName
            binding.textLocation.text = destination.city
            binding.textPrice.text = "$${destination.price}"
            binding.textRating.text = "Rating: ${destination.rating}"
        }
    }

    class DestinationDiffCallback : DiffUtil.ItemCallback<TravelResponseItem>() {
        override fun areItemsTheSame(oldItem: TravelResponseItem, newItem: TravelResponseItem): Boolean {
            return oldItem.placeId == newItem.placeId
        }

        override fun areContentsTheSame(oldItem: TravelResponseItem, newItem: TravelResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}