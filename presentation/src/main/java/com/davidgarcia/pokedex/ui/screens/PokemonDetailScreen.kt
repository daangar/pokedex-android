package com.davidgarcia.pokedex.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.davidgarcia.pokedex.mvi.PokemonDetailIntent
import com.davidgarcia.pokedex.mvi.PokemonDetailState
import com.davidgarcia.pokedex.presentation.R
import com.davidgarcia.pokedex.ui.components.StatIndicator
import com.davidgarcia.pokedex.ui.theme.PokedexColor
import com.davidgarcia.pokedex.ui.utils.toColor
import com.davidgarcia.pokedex.viewmodel.PokemonDetailViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    id: Int,
    name: String,
    url: String?,
    onBack: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(PokemonDetailIntent.LoadDetail(id))
    }

    PokemonDetailScreenContent(state, id, name, url, sharedTransitionScope, animatedVisibilityScope, onBack)
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailScreenContent(
    state: PokemonDetailState,
    id: Int,
    name: String,
    url: String?,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    Icon(
                        imageVector =  Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = PokedexColor.NavyBlue,
                        modifier = Modifier.clickable { onBack() }
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = name,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = PokedexColor.NavyBlue
                        )
                    )
                },
                actions = {
                    Text(
                        text = "#${id.toString().padStart(3, '0')}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = PokedexColor.SmokeGray
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            var color by remember { mutableStateOf("white") }
            Box(modifier = Modifier.wrapContentHeight()) {

                Box(modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(color.toColor(), RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .height(157.dp))

                with(sharedTransitionScope) {
                    AsyncImage(
                        model = url,
                        contentDescription = name,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image${id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .align(Alignment.TopCenter)
                            .size(width = 265.dp, height = 230.86.dp),
                        contentScale = ContentScale.Inside
                    )
                }
            }


            when(state) {
                is PokemonDetailState.Loading -> CircularProgressIndicator(modifier = Modifier.align(
                    Alignment.CenterHorizontally))
                is PokemonDetailState.Success -> {
                    val detail = state.detail
                    color = detail.color

                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(detail.types) { type ->
                            Text(
                                text = type,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .background(Color.LightGray)
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = PokedexColor.NavyBlue,
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Altura: ${detail.height}  Peso: ${detail.weight}",
                        textAlign = TextAlign.Center,
                        color = PokedexColor.NavyBlue,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        text = detail.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PokedexColor.MediumGray,
                            textAlign = TextAlign.Justify
                        )
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 10.dp),
                        text = stringResource(R.string.stats),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = PokedexColor.NavyBlue
                        )
                    )

                    detail.stats.forEach { (key, value) ->
                        StatIndicator(
                            modifier = Modifier.padding(bottom = 10.dp),
                            key = key,
                            currentValue = value
                        )
                    }
                }
                is PokemonDetailState.Error -> {
                    Text(stringResource(R.string.load_detail_error), color = PokedexColor.NavyBlue)
                }
            }

        }
    }
}