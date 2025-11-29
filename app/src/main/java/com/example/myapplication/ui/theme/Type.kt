package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_semibold, FontWeight.SemiBold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal
    ),
    displayMedium = TextStyle(fontFamily = Roboto),
    displaySmall = TextStyle(fontFamily = Roboto),

    headlineLarge = TextStyle(fontFamily = Roboto),
    headlineMedium = TextStyle(fontFamily = Roboto),
    headlineSmall = TextStyle(fontFamily = Roboto),

    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = TextStyle(fontFamily = Roboto),
    titleSmall = TextStyle(fontFamily = Roboto),

    bodyLarge = TextStyle(fontFamily = Roboto),
    bodyMedium = TextStyle(fontFamily = Roboto),
    bodySmall = TextStyle(fontFamily = Roboto),

    labelLarge = TextStyle(fontFamily = Roboto),
    labelMedium = TextStyle(fontFamily = Roboto),
    labelSmall = TextStyle(fontFamily = Roboto)
)
