package com.example.tvshowsappmvvm.data


import androidx.paging.*

import com.example.tvshowsappmvvm.api.MoviesApi
import com.example.tvshowsappmvvm.api.TvShow
import com.example.tvshowsappmvvm.paging.TvShowPagingSource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class Repository @Inject constructor(
    private val moviesApi: MoviesApi
    //private val tvShowDatabase: TvShowDatabase
){
    @OptIn(ExperimentalPagingApi::class)
    fun getTvShows(): Flow<PagingData<TvShow>> = Pager(
        config = PagingConfig(20,100),
        //remoteMediator = TvShowsRemoteMediator(moviesApi,tvShowDatabase),
        pagingSourceFactory = { TvShowPagingSource(moviesApi) }
    ).flow


}