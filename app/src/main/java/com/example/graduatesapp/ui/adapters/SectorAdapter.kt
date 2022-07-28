package com.example.graduatesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.graduatesapp.databinding.SectorItemBinding
import com.example.graduatesapp.ui.models.Sector

class SectorAdapter(val onClick:(item: Sector)->Unit):ListAdapter<Sector,SectorAdapter.SectorViewHolder>(SectorComparator()) {

    class SectorComparator:DiffUtil.ItemCallback<Sector>() {
        override fun areItemsTheSame(oldItem: Sector, newItem: Sector): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Sector, newItem: Sector): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class SectorViewHolder(val binding:SectorItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Sector,onClick:(item: Sector)->Unit) {
            var name = item.name
            if(name.length > 24) {
                name = name.substring(0,23)+"..."
            }
            binding.sectorName.text = name
            var description = item.description
            if(description.length > 26 ) {
                description = description.substring(0,25)+"...."
            }
            binding.description.text = description
            binding.sectorItem.setOnClickListener {
                onClick(item)
            }
        }
        companion object {
            fun from(parent:ViewGroup):SectorViewHolder {
                val binding = SectorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return SectorViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorViewHolder {
        return SectorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorViewHolder, position: Int) {
        holder.bind(getItem(position),onClick)
    }
}