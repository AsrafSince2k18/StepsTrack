package com.since.presentaction.designsystem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val DarkColorScheme = darkColorScheme(
    primary = Yellow,
    secondary = PurpleEnd,
    onSecondary = Color.Black,
    background = RunsBlack,
    surface = RunsDarkGray,
    tertiary = RunsWhite,
    onPrimary = RunsBlack,
    onBackground = RunsWhite,
    onSurface = RunsWhite,
    onSurfaceVariant = RunsGray,
    error = RunsDarkRed
)



@Composable
fun StepsTrackerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}