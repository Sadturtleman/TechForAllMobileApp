package com.example.myapplication.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.screen.component.FavoriteCard
import com.example.myapplication.ui.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val favoriteList by viewModel.favoriteList.collectAsState()
    val colorList = listOf(
        Color(0xFFFFC933),
        Color(0xFFBAD6FE),
        Color(0xFFBCF3CC),
        Color(0xFFFEBBB3)
    )
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)  // 세로 간격 조금 더
    ) {
        items(favoriteList) { favorite ->
            FavoriteCard(
                favorite = favorite,
                color = colorList.random(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

}