package com.davidgarcia.pokedex.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.davidgarcia.pokedex.ui.screens.PokemonDetailScreen
import com.davidgarcia.pokedex.ui.screens.PokemonListScreen


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHost() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "list"
        ) {
            composable("list") {
                PokemonListScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onPokemonClick = { pokemon ->
                        val rawUrl = pokemon.imageUrl
                        val id = pokemon.id
                        val name = pokemon.name
                        val encoded = Uri.encode(rawUrl)
                        navController.navigate("details/$id/$name/$encoded")
                    }
                )
            }

            composable(
                "details/{id}/{name}/{url}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("name") { type = NavType.StringType },
                    navArgument("url") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val name = backStackEntry.arguments?.getString("name").orEmpty()
                val encodedUrl = backStackEntry.arguments?.getString("url")
                val imageUrl = Uri.decode(encodedUrl)
                PokemonDetailScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    id = id,
                    name = name,
                    url = imageUrl,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}