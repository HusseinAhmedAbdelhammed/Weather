package com.example.weather.adapters

import androidx.recyclerview.widget.DiffUtil

class DailyDiffUtil: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }
}