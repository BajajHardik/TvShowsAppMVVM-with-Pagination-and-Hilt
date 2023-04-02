package com.example.tvshowsappmvvm.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.tvshowsappmvvm.api.TvShow

class TvShowsComparator: DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow)=
        oldItem == newItem
}