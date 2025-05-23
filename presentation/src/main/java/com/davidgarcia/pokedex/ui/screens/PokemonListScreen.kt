package com.davidgarcia.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.mvi.PokemonIntent
import com.davidgarcia.pokedex.mvi.PokemonState
import com.davidgarcia.pokedex.presentation.R
import com.davidgarcia.pokedex.ui.components.BoldAnnotatedText
import com.davidgarcia.pokedex.ui.components.PokemonCard
import com.davidgarcia.pokedex.ui.components.PokemonListTopBar
import com.davidgarcia.pokedex.ui.theme.PokedexColor
import com.davidgarcia.pokedex.ui.theme.PokedexTheme
import com.davidgarcia.pokedex.viewmodel.PokemonViewModel

@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    PokemonListScreenContent(state) {
        viewModel.sendIntent(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListScreenContent(
    state: PokemonState,
    sendIntent: (PokemonIntent) -> Unit
) {
    val query = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            PokemonListTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            BoldAnnotatedText(
                modifier = Modifier.padding(top = 20.dp).requiredHeight(34.dp),
                textRes = R.string.greeting_full,
                boldRes = R.string.greeting_bold_part,
                style = MaterialTheme.typography.titleMedium.copy(color = PokedexColor.AbyssBlue)
            )

            // Search Bar
            TextField(
                value = query.value,
                onValueChange = { query.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 20.dp)
                    .border(1.dp, PokedexColor.SmokeGray, RoundedCornerShape(30.dp)),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PokedexColor.CharcoalGray
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(PokedexColor.VibrantYellow)
                            .clickable {
                                sendIntent(PokemonIntent.Search(query.value))
                            }
                            .padding(7.dp),
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = PokedexColor.NavyBlue
                    )
                },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        sendIntent(PokemonIntent.Search(query.value))
                    }
                )
            )

            when (state) {
                is PokemonState.Loading -> CircularProgressIndicator()
                is PokemonState.Success -> {
                    val list = state.list
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(list) { pokemon ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                PokemonCard(pokemon)
                            }
                        }
                    }
                }
                is PokemonState.Error -> {
                    Text("Error: ${state.throwable.message}")
                }
            }
        }
    }
}




@Suppress("unused")
class PokemonListScreenPreviews {

    @Preview(showBackground = true)
    @Composable
    fun PokemonListScreenPreview() {
        PokedexTheme {
            PokemonListScreenContent(
                state = PokemonState.Success(
                    listOf(Pokemon(1, "Pikachu", null))
                )
            ) {}
        }
    }
}
