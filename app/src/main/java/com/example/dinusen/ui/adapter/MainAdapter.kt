package com.example.dinusen.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dinusen.BuildConfig
import com.example.dinusen.data.remote.response.HomeItem
import com.example.dinusen.databinding.ItemRowEventBinding

class MainAdapter(private val onItemClickCallback: OnItemClickCallback): ListAdapter<HomeItem, MainAdapter.MainViewHolder>(DiffCallbackEvent) {
    inner class MainViewHolder(var binding: ItemRowEventBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val event = getItem(position % currentList.size)

        holder.binding.apply {
            Glide.with(root)
                .load("${BuildConfig.BASE_URL_IMAGE}${event.image}")
                .into(ivEvent)

            root.setOnClickListener {
                onItemClickCallback.onItemClicked(event.eventId)
            }
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    interface OnItemClickCallback {
        fun onItemClicked(eventId: Int)
    }
}