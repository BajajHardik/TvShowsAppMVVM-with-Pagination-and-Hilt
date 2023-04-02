package com.example.tvshowsappmvvm

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observer(): Flow<Status>

    enum class Status{
        Available,UnAvailable,Losing,Lost
    }
}