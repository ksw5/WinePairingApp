package com.example.winepairing.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.winepairing.R
import com.example.winepairing.model.data.ProductMatches



class WineAdapter : ListAdapter<ProductMatches, WineAdapter.WineViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.favorite_wines_item, parent, false)
        return WineViewHolder(view)
    }

    override fun onBindViewHolder(holder: WineViewHolder, position: Int) {
        val wine = getItem(position)
        holder.bind(wine)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProductMatches>() {
            override fun areItemsTheSame(
                oldItem: ProductMatches,
                newItem: ProductMatches
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductMatches,
                newItem: ProductMatches
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class WineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productMatches: ProductMatches) {
            val wineImage = itemView.findViewById<ImageView>(R.id.wine_image)
            Glide.with(itemView.context).load(productMatches.imageUrl).centerCrop().into(wineImage)

        }
    }
}