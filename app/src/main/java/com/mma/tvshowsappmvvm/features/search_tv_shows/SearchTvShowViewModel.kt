package com.example.tvshowsappmvvm.features.search_tv_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tvshowsappmvvm.data.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTvShowViewModel @Inject constructor(
    repository: SearchRepository
):ViewModel() {
    private val currentQuery = MutableStateFlow<String?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults = currentQuery.flatMapLatest { query->
        query?.let {
            repository.getSearchTvShows(it)
        }?: emptyFlow()
    }.cachedIn(viewModelScope)

    fun onSearchQuerySubmit(query:String){
        currentQuery.value=query
    }

   // val list = repository.getTvShows().cachedIn(viewModelScope)


}