package com.example.myapplication.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.factory.FavoriteFactory
import com.example.myapplication.data.model.Favorite

@Composable
fun FavoriteCard(
    favorite: Favorite,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}   // ← 추가
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(
                elevation = 4.dp,
                ambientColor = Color(0x40000000),
                spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(color)
            .clickable { onClick() },   // ← 클릭 가능
        contentAlignment = Alignment.Center
    ) {
        AutoResizeText(
            text = favorite.title,
            maxFontSize = 28.sp
        )
    }
}



@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 28.sp,
    minFontSize: TextUnit = 12.sp
) {
    var fontSize by remember { mutableStateOf(maxFontSize) }
    var ready by remember { mutableStateOf(false) }

    Text(
        text = text,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        softWrap = true,
        fontWeight = FontWeight.SemiBold,
        maxLines = 2,
        overflow = TextOverflow.Clip,
        modifier = modifier.drawWithContent {
            if (ready) drawContent()
        },
        onTextLayout = { result ->
            if (!ready && (result.didOverflowWidth || result.didOverflowHeight)) {
                val next = fontSize * 0.9f
                if (next >= minFontSize) {
                    fontSize = next
                } else {
                    ready = true
                }
            } else {
                ready = true
            }
        }
    )
}


@Preview
@Composable
private fun FavoriteCardPreview() {
    val factory = FavoriteFactory()
    val favoriteList = factory.getFavoriteList()

    FavoriteCard(favoriteList[0], Color(0xFFFEBBB3))
}