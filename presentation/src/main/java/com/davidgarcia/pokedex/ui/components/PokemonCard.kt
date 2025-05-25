package com.davidgarcia.pokedex.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.ui.theme.PokedexColor
import com.davidgarcia.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onPokemonClick: (Pokemon) -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .size(165.dp)
            .clickable { onPokemonClick(pokemon) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier.padding(20.dp).fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PokedexColor.SmokeGray,
                )
                AsyncImage(
                    modifier = Modifier
                        .size(81.dp)
                        .align(Alignment.CenterHorizontally),
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = PokedexColor.NavyBlue
                )
            }
        }
    }
}



@Suppress("unused")
class PokemonCardPreviews {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Preview
    @Composable
    fun PokemonCardPreview() {
        PokedexTheme {
            PokemonCard(
                pokemon = Pokemon(1, "pikachu", null)
            ) {}
        }
    }
}
