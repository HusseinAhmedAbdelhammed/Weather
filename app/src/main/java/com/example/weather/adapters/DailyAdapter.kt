package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.DailyRawBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.ImageLoader

class DailyAdapter(var context: Context?): ListAdapter<String, DailyViewHolder>(DailyDiffUtil()) {
    lateinit var binding:DailyRawBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder{
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding=DailyRawBinding.inflate(inflater,parent,false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val current=getItem(position)
        val splitCurrent=current.split(Consts.DAILY_DELEMETER)
        binding.tvDate.text=splitCurrent[1]
        ImageLoader.loadImage(context,splitCurrent[2],binding.ivDayIcon)
        binding.tvWeatherDescription.text=splitCurrent[3]
        binding.tvTempMinMax.text=splitCurrent[0]
        binding.tvTempDiscrimination.text="c"
    }
}