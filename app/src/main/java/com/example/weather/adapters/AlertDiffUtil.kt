package com.example.weather.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.fakeentity.AlertDomainEntity

class AlertDiffUtil : DiffUtil.ItemCallback<AlertDomainEntity>() {
    override fun areItemsTheSame(oldItem: AlertDomainEntity, newItem: AlertDomainEntity): Boolean {
        return oldItem.endTime == newItem.endTime
    }

    override fun areContentsTheSame(oldItem: AlertDomainEntity, newItem: AlertDomainEntity): Boolean {
        return oldItem == newItem
    }
}