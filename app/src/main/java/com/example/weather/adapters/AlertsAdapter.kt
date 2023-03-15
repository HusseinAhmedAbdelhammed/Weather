package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.weather.databinding.AlertRowBinding
import com.example.weather.fragments.OnDeleteAlert
import com.example.weather.helpers.WeatherHelper.getDateTimeLong

class AlertsAdapter(var context: Context?,
                    var onDeleteAlert: OnDeleteAlert): ListAdapter<AlertDomainEntity, AlertViewHolder>(AlertDiffUtil()) {
    lateinit var binding: AlertRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= AlertRowBinding.inflate(inflater,parent,false)
        return AlertViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val current=getItem(position)
        holder.binding.deleteThisAlertRow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onDeleteAlert.delete(current)
            }

        })
        holder.binding.startAlertDateTextViewRow.text= getDateTimeLong(current.startTime,"EEE, MMM d","en")
        holder.binding.startAlertTimeTextViewRow.text = getDateTimeLong(current.startTime, "hh:mm a", "en")

        holder.binding.endAlertTimeTextViewRow.text = getDateTimeLong(current.endTime, "hh:mm a", "en")
        holder.binding.endAlertDateTextViewRow.text= getDateTimeLong(current.endTime, "EEE, MMM d", "en")

    }
}