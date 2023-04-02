package com.example.tvshowsappmvvm.features.tv_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tvshowsappmvvm.api.TvShow
import com.example.tvshowsappmvvm.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    repository: Repository
):ViewModel() {
    //private val eventChannel = Channel<Event>()
    //val events = eventChannel.receiveAsFlow()

    private val tvShowsFlow = MutableStateFlow<List<TvShow>>(emptyList())
    @OptIn(ExperimentalCoroutinesApi::class)
    val tvShowResults = tvShowsFlow.flatMapLatest {
        repository.getTvShows()
    }.cachedIn(viewModelScope)

   // val list = repository.getTvShows().cachedIn(viewModelScope)
//    fun showToast(t:String){
//        viewModelScope.launch {
//            eventChannel.send(Event.ShowErrorMessage(t))
//        }
//    }


//    sealed class Event{
//        data class ShowErrorMessage(val t:String) : Event()
//    }


}