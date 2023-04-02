package com.example.tvshowsappmvvm.Module


import com.example.tvshowsappmvvm.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTvShowsApi(retrofit: Retrofit):MoviesApi =
        retrofit.create()


}