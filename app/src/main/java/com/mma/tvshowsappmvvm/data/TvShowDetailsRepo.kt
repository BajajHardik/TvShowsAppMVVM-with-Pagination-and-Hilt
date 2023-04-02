package com.example.tvshowsappmvvm.data

import androidx.lifecycle.MutableLiveData
import com.example.tvshowsappmvvm.api.MoviesApi
import com.example.tvshowsappmvvm.api.TvShowDetailsDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TvShowDetailsRepo @Inject constructor(
    private val moviesApi: MoviesApi
) {
    fun getUser(userId: String): MutableLiveData<TvShowDetailsDto?> {
        val data = MutableLiveData<TvShowDetailsDto?>()
        moviesApi.getTVShowDetails(userId).enqueue(object : Callback<TvShowDetailsDto> {
            override fun onResponse(call: Call<TvShowDetailsDto>, response: Response<TvShowDetailsDto>) {
              data.value=response.body()
            }
            override fun onFailure(call: Call<TvShowDetailsDto>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }



}