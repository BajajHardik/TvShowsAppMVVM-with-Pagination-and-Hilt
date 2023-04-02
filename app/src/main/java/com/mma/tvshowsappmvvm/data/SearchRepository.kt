package com.example.tvshowsappmvvm.data

import androidx.paging.*
import com.example.tvshowsappmvvm.api.MoviesApi
import com.example.tvshowsappmvvm.api.TvShow
import com.example.tvshowsappmvvm.paging.TvShowSearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val moviesApi: MoviesApi
){

    fun getSearchTvShows(query:String): Flow<PagingData<TvShow>> = Pager(
        config = PagingConfig(20,100),
        pagingSourceFactory = { TvShowSearchPagingSource(moviesApi,query) }
    ).flow
}