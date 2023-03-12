package com.example.tmdb.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.model.Search
import com.example.tmdb.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _searchResult = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val searchSearch: State<Flow<PagingData<Search>>> = _searchResult

    fun searchAll(searchParam: String) {
        viewModelScope.launch {
            _searchResult.value = repository.getSearchResults(searchParam).cachedIn(viewModelScope)
        }
    }
}
