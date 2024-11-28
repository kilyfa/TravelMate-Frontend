package com.example.travelmate.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmate.data.response.DataItem
import com.example.travelmate.databinding.ItemRecommendationBinding

class ResultAdapter(
    private val onItemClick: (DataItem) -> Unit
) : ListAdapter<DataItem, ResultAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    class RecommendationViewHolder(
        private val binding: ItemRecommendationBinding,
        private val onItemClick: (DataItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: DataItem) {
            binding.apply {
                textName.text = dataItem.name
                textLocation.text = dataItem.city
                textRating.text = "Rating: ${dataItem.rating}"
                textPrice.text = "Price: ${dataItem.price}"
                textDescription.text = dataItem.category

                // Handle item click
                root.setOnClickListener {
                    onItemClick(dataItem)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
