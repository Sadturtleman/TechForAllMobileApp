package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.SearchResult
import com.example.myapplication.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {

    private val _results = MutableStateFlow<List<SearchResult>>(emptyList())
    val results = _results.asStateFlow()

    private val _placeName = MutableStateFlow("")
    val placeName = _placeName.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    fun setPlaceInfo(place: String, addr: String) {
        _placeName.value = place
        _address.value = addr
    }
    fun search(query: String) {
        viewModelScope.launch {
            val list = repo.search(query)
            _results.value = list     // ← 이거 넣어줘야 UI에 전달됨
        }
    }
}
