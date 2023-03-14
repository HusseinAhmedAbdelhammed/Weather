package com.example.weather.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.fakeentity.FavDomainEntity

class FavDiffUtil:DiffUtil.ItemCallback<FavDomainEntity>(){
    override fun areItemsTheSame(oldItem: FavDomainEntity, newItem: FavDomainEntity): Boolean {
        return oldItem.city==newItem.city
    }

    override fun areContentsTheSame(oldItem: FavDomainEntity, newItem: FavDomainEntity): Boolean {
        return oldItem==newItem
    }
}