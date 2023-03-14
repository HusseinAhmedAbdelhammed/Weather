package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.databinding.HourlyItemBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.ImageLoader

class HourlyAdapter(var context: Context?): ListAdapter<String, HourlyViewHolder>(DailyDiffUtil()) {
    lateinit var binding: HourlyItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= HourlyItemBinding.inflate(inflater,parent,false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val current=getItem(position)
        val splitCurrent=current.split(Consts.HOURLY_DELEMETER)
        binding.hourTime.text=splitCurrent[3]
        binding.tempHour.text=splitCurrent[0]+"/"+splitCurrent[1]
        ImageLoader.loadImage(context,splitCurrent[2],binding.tempIconHourRow)
    }
}