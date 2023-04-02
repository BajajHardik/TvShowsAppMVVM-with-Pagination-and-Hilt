package com.example.tvshowsappmvvm.paging

import androidx.paging.PagingState
import com.example.tvshowsappmvvm.api.MoviesApi
import com.example.tvshowsappmvvm.api.TvShow

class TvShowPagingSource(private val moviesApi: MoviesApi): androidx.paging.PagingSource<Int, TvShow>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try{
            val position = params.key ?: 1
            val response = moviesApi.getPopularTvShows(position)
            return LoadResult.Page(
                data = response.tv_shows,
                prevKey = if(position==1) null else position-1,
                nextKey = if(position == response.pages) null else position+1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

}