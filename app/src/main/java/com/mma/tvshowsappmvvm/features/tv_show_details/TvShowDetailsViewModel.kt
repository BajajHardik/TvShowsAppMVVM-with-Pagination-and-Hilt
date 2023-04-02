package com.example.tvshowsappmvvm.features.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvshowsappmvvm.api.TvShowDetailsDto
import com.example.tvshowsappmvvm.data.TvShowDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val repository: TvShowDetailsRepo
):ViewModel() {

    fun getTVShowDetails(tvShowId: String?): MutableLiveData<TvShowDetailsDto?>? {
        return tvShowId?.let { repository.getUser(it) }
    }

}