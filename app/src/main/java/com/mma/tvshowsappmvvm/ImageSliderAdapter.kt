package com.example.tvshowsappmvvm


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mma.tvshowsappmvvm.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private val sliderImages: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val sliderImageBinding= ItemContainerSliderImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageSliderViewHolder(sliderImageBinding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSlideImage(sliderImages[position])
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }

    inner class ImageSliderViewHolder(private val binding: ItemContainerSliderImageBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bindSlideImage(imageURL: String) {
            binding.apply {
                Glide.with(itemView)
                    .load(imageURL)
                    .into(image)

            }
        }
    }

}