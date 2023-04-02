package com.example.tvshowsappmvvm.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tvshowsappmvvm.api.TvShow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM popular_tv_shows ORDER BY id")
    fun getTvShows():PagingSource<Int, TvShow>

    @Query("SELECT * FROM popular_tv_shows WHERE name=:query")
    fun getSearchTvShows(query:String):PagingSource<Int,TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows:List<TvShow>)

    @Query("DELETE FROM popular_tv_shows")
    suspend fun deleteTvShows()
}