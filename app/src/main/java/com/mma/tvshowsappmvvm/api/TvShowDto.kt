package com.example.tvshowsappmvvm.api

data class TvShowDto(
    val pages:Int,
    val page:Int,
    val tv_shows: List<TvShow>
)