package com.example.tvshowsappmvvm.features.tv_shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

import com.example.tvshowsappmvvm.api.TvShow
import com.example.tvshowsappmvvm.shared.TvShowsComparator
import com.example.tvshowsappmvvm.shared.TvShowsViewHolder
import com.mma.tvshowsappmvvm.databinding.ItemContainerBinding

class TvShowsAdapter(
    private val onItemClick:(TvShow) -> Unit
):PagingDataAdapter<TvShow, TvShowsViewHolder>(TvShowsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val binding = ItemContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvShowsViewHolder(binding,
            onItemClick = {position->
                val article = getItem(position)
                if(article!=null){
                    onItemClick(article)
                }
            })
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem!=null){
            holder.bind(currentItem)
        }
    }

}