package com.example.tvshowsappmvvm.api

import com.example.tvshowsappmvvm.api.TvShowDetailsDto
import com.example.tvshowsappmvvm.api.TvShowDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    companion object{
        const val BASE_URL = "https://www.episodate.com/api/"
    }

    @GET("most-popular")
    suspend fun getPopularTvShows(@Query("page") page:Int): TvShowDto

    @GET("search")
    suspend fun searchPopularTvShows(
        @Query("q") query: String,
        @Query("page") page:Int
    ):TvShowDto

//    @GET("show-details")
//    fun getTVShowDetails(@Query("q") tvShowId: String?): Call<TvShowDetailsDto>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String?): Call<TvShowDetailsDto>

}