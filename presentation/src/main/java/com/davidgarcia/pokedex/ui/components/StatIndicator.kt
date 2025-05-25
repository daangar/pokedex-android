package com.davidgarcia.pokedex.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidgarcia.pokedex.ui.theme.PokedexColor
import com.davidgarcia.pokedex.ui.theme.PokedexTheme
import com.davidgarcia.pokedex.presentation.R as RUI

@Composable
fun StatIndicator(
    modifier: Modifier = Modifier,
    key: String,
    currentValue: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val (stat, maxValue) = when (key) {
            "hp" -> Pair(RUI.string.hp, 255)
            "attack" -> Pair(RUI.string.atack, 190)
            "defense" -> Pair(RUI.string.defense, 230)
            "special-attack" -> Pair(RUI.string.special_attack, 194)
            "special-defense" -> Pair(RUI.string.special_defense, 230)
            "speed" -> Pair(RUI.string.speed, 180)
            else -> Pair(RUI.string.other, 100)
        }

        Text(
            modifier = Modifier.width(121.dp),
            text = stringResource(stat),
            style = MaterialTheme.typography.labelMedium.copy(
                color = PokedexColor.CharcoalGray
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val targetFraction = currentValue.coerceIn(0, maxValue) / maxValue.toFloat()
        var playAnimation by remember { mutableStateOf(false) }

        val progress by animateFloatAsState(
            targetValue = if (playAnimation) targetFraction else 0f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )

        Box(modifier = Modifier.width(160.dp)) {
            LinearProgressIndicator(
                modifier = Modifier.height(14.dp),
                progress = { 0f },
                trackColor = Color(0xFF9BB8D3),
                drawStopIndicator = {}
            )

            LinearProgressIndicator(
                modifier = Modifier.height(14.dp),
                progress = { progress },
                color = Color(0xFF005FFF),
                trackColor = Color.Transparent,
                drawStopIndicator = {}
            )
        }


        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = currentValue.toString().padStart(3, '0'),
            style = MaterialTheme.typography.labelMedium.copy(
                color = PokedexColor.CharcoalBlack,
                fontWeight = FontWeight.Bold
            )
        )

        LaunchedEffect(Unit) {
            playAnimation = true
        }
    }
}

@Suppress("unused")
class StatIndicatorPreviews {

    @Preview(showBackground = true)
    @Composable
    fun StatIndicatorPreview() {
        PokedexTheme {
            StatIndicator(key = "special-defense", currentValue = 100)
        }
    }
}
