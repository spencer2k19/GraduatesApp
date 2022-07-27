package com.example.graduatesapp.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.graduatesapp.R
import com.example.graduatesapp.databinding.GraduateItemBinding
import com.example.graduatesapp.helper.EndPoints
import com.example.graduatesapp.ui.models.Graduate

class GraduateAdapter(val onClick:(item:Graduate)->Unit):
    ListAdapter<Graduate,GraduateAdapter.GraduateViewHolder>(GraduateComparator()) {

    class GraduateComparator:DiffUtil.ItemCallback<Graduate>() {
        override fun areItemsTheSame(oldItem: Graduate, newItem: Graduate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Graduate, newItem: Graduate): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class GraduateViewHolder(val binding:GraduateItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Graduate,onClick:(item:Graduate)->Unit) {
            binding.name.text = "${item.lastName} ${item.firstName}"
            var description = item.bio
            if(description.length > 26 ) {
                description = description.substring(0,25)+"...."
            }
            binding.bio.text = description
            item.avatar?.apply {
                val url = "${EndPoints.BASE_URL_FILES}/$this"
                url.let {
                    Glide.with(binding.root)
                        .load(Uri.parse(it))
                        .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_broken_image))
                        .into(binding.avatar)
                }
            }
            binding.graduateItem.setOnClickListener {
                onClick(item)
            }

        }
        companion object {
            fun from(parent:ViewGroup):GraduateViewHolder {
                val binding = GraduateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return GraduateViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraduateViewHolder {
       return GraduateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GraduateViewHolder, position: Int) {
       holder.bind(getItem(position),onClick)
    }
}