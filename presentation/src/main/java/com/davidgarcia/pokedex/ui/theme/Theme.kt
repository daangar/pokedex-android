package com.davidgarcia.pokedex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    surface = PokedexColor.WhisperWith,
    background = PokedexColor.WhisperWith,
    primary = PokedexColor.RoyalBlue,
    secondary = PokedexColor.WhisperWith,
    tertiary = PokedexColor.WhisperWith
)

@Composable
fun PokedexTheme(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(LightColorScheme.surface)
        systemUiController.setNavigationBarColor(LightColorScheme.surface)
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}