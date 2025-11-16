package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.factory.FavoriteFactory
import com.example.myapplication.data.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteFactory: FavoriteFactory
): ViewModel(){
    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()


    init {
        loadFavoriteList()
    }

    private fun loadFavoriteList(){
        _favoriteList.value = favoriteFactory.getFavoriteList()
    }
}