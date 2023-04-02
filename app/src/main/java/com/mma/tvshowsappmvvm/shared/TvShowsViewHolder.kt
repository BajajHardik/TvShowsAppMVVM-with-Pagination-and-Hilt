package com.example.tvshowsappmvvm.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvshowsappmvvm.api.TvShow
import com.mma.tvshowsappmvvm.databinding.ItemContainerBinding

class TvShowsViewHolder(
    private val binding: ItemContainerBinding,
    private val onItemClick:(Int) -> Unit

):RecyclerView.ViewHolder(binding.root) {
    fun bind(tvShow: TvShow){
        binding.apply {
            Glide.with(itemView)
                .load(tvShow.image_thumbnail_path)
                .into(imageTVshow)

            MovieName.text = tvShow.name?:""
            textNetwork.text = "${tvShow.network} (${tvShow.country})"
            textStarted.text = tvShow.start_date
            textStatus.text = tvShow.status
        }
    }
    init {
        binding.apply {
            root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    onItemClick(position)
                }
            }
        }
    }

}