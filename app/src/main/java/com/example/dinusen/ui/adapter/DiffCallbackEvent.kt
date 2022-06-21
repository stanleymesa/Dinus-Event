package com.example.dinusen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.dinusen.data.remote.response.HomeItem

object DiffCallbackEvent: DiffUtil.ItemCallback<HomeItem>() {
    override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }
}