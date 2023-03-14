package com.example.weather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.weather.databinding.FavouriteRowBinding
import com.example.weather.fragments.FavInterface

class FavAdapter(var context: Context?,var fav:FavInterface): ListAdapter<FavDomainEntity, FavViewHolder>(FavDiffUtil()) {
    lateinit var binding: FavouriteRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= FavouriteRowBinding.inflate(inflater,parent,false)
        return FavViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val current=getItem(position)
        holder.binding.deleteThisRow.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                fav.delete(current)
            }

        })
        holder.binding.textCityFavName.text=current.city

    }
}