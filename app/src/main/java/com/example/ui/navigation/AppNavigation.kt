package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.AskMemoryScreen
import com.example.ui.screens.AuthorScreen
import com.example.ui.screens.BlogScreen
import com.example.ui.screens.BlumenauLandmarkDetailScreen
import com.example.ui.screens.BlumenauScreen
import com.example.ui.screens.BookReaderScreen
import com.example.ui.screens.BookReadingListScreen
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.ChaptersScreen
import com.example.ui.screens.CharacterDetailScreen
import com.example.ui.screens.CharactersScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InteractiveMapScreen
import com.example.ui.screens.JourneyScreen
import com.example.ui.screens.LocationDetailScreen
import com.example.ui.screens.LocationsScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.MusicScreen
import com.example.ui.screens.WorkScreen
import com.example.ui.screens.game.MemoryGameHubScreen
import com.example.ui.screens.game.MemoryGamePlayScreen
import com.example.ui.screens.puzzle.PuzzleGameScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable("main_screen") {
            MainScreen(
                onExploreUniverse = { navController.navigate("work") },
                onListenMusic = { navController.navigate("music") }
            )
        }

        composable("music") {
            MusicScreen(
                onBack = { navController.popBackStack() },
                onNavigateBlog = { navController.navigate("blog") }
            )
        }

        composable("work") {
            WorkScreen(
                onBack = { navController.popBackStack() },
                onNavigateChapters = { navController.navigate("chapters") },
                onNavigateJourney = { navController.navigate("journey") }
            )
        }

        composable("journey") {
            JourneyScreen(
                onBack = { navController.popBackStack() },
                onNavigateCharacter = { charId -> navController.navigate("character_detail/$charId") },
                onNavigateCharacters = { navController.navigate("characters") }
            )
        }

        composable("characters") {
            CharactersScreen(
                onBack = { navController.popBackStack() },
                onSelectCharacter = { charId -> navController.navigate("character_detail/$charId") },
                onNavigateLocations = { navController.navigate("locations") }
            )
        }

        composable(
            route = "character_detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId") ?: "jonatas"
            CharacterDetailScreen(
                characterId = characterId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("locations") {
            LocationsScreen(
                onBack = { navController.popBackStack() },
                onSelectLocation = { locId -> navController.navigate("location_detail/$locId") },
                onNavigateMap = { navController.navigate("map") }
            )
        }

        composable(
            route = "location_detail/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId") ?: "blumenau"
            LocationDetailScreen(
                locationId = locationId,
                onBack = { navController.popBackStack() },
                onNavigateMap = { navController.navigate("map") }
            )
        }

        composable("map") {
            InteractiveMapScreen(
                onBack = { navController.popBackStack() },
                onSelectLocation = { locId -> navController.navigate("location_detail/$locId") },
                onNavigateChapters = { navController.navigate("chapters") }
            )
        }

        composable("chapters") {
            ChaptersScreen(
                onBack = { navController.popBackStack() },
                onSelectChapter = { num -> navController.navigate("chapter_detail/$num") },
                onNavigateReadingList = { navController.navigate("reading_list") }
            )
        }

        composable(
            route = "chapter_detail/{chapterNumber}",
            arguments = listOf(navArgument("chapterNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val chapterNumber = backStackEntry.arguments?.getInt("chapterNumber") ?: 1
            ChapterDetailScreen(
                chapterNumber = chapterNumber,
                onBack = { navController.popBackStack() },
                onNavigateChapter = { targetNum ->
                    navController.popBackStack()
                    navController.navigate("chapter_detail/$targetNum")
                }
            )
        }

        composable("blumenau") {
            BlumenauScreen(
                onBack = { navController.popBackStack() },
                onNavigateMusic = { navController.navigate("music") },
                onNavigateMemoryGame = { navController.navigate("memory_game") },
                onNavigatePuzzleGame = { navController.navigate("puzzle_game") },
                onSelectLandmark = { landmarkId -> navController.navigate("blumenau_landmark/$landmarkId") }
            )
        }

        composable(
            route = "blumenau_landmark/{landmarkId}",
            arguments = listOf(navArgument("landmarkId") { type = NavType.StringType })
        ) { backStackEntry ->
            val landmarkId = backStackEntry.arguments?.getString("landmarkId") ?: "catedral"
            BlumenauLandmarkDetailScreen(
                landmarkId = landmarkId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("blog") {
            BlogScreen(
                onBack = { navController.popBackStack() },
                onSelectArticle = { artId -> navController.navigate("article_detail/$artId") },
                onNavigateAuthor = { navController.navigate("author") }
            )
        }

        composable(
            route = "article_detail/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: "art-1"
            ArticleDetailScreen(
                articleId = articleId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("author") {
            AuthorScreen(
                onBack = { navController.popBackStack() },
                onNavigateHome = {
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }

        composable("reading_list") {
            BookReadingListScreen(
                onBack = { navController.popBackStack() },
                onSelectChapter = { num -> navController.navigate("read_chapter/$num") },
                onNavigateAskMemory = { navController.navigate("ask_memory") }
            )
        }

        composable(
            route = "read_chapter/{chapterNumber}",
            arguments = listOf(navArgument("chapterNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val chapterNumber = backStackEntry.arguments?.getInt("chapterNumber") ?: 1
            BookReaderScreen(
                chapterNumber = chapterNumber,
                onBack = { navController.popBackStack() },
                onNavigateChapter = { targetNum ->
                    navController.popBackStack()
                    navController.navigate("read_chapter/$targetNum")
                },
                onNavigateList = {
                    navController.popBackStack("reading_list", inclusive = false)
                }
            )
        }

        composable("ask_memory") {
            AskMemoryScreen(
                onBack = { navController.popBackStack() },
                onNavigateBlumenau = { navController.navigate("blumenau") }
            )
        }

        composable("memory_game") {
            MemoryGameHubScreen(
                onBack = { navController.popBackStack() },
                onStartGame = { level ->
                    navController.navigate("memory_game_play/$level/normal")
                },
                onStartSpecialMode = { mode ->
                    navController.navigate("memory_game_play/1/$mode")
                }
            )
        }

        composable(
            route = "memory_game_play/{levelNumber}/{specialMode}",
            arguments = listOf(
                navArgument("levelNumber") { type = NavType.IntType },
                navArgument("specialMode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val levelNumber = backStackEntry.arguments?.getInt("levelNumber") ?: 1
            val specialModeArg = backStackEntry.arguments?.getString("specialMode") ?: "normal"
            val specialMode = if (specialModeArg == "normal") null else specialModeArg

            MemoryGamePlayScreen(
                levelNumber = levelNumber,
                specialMode = specialMode,
                onBack = { navController.popBackStack() },
                onNavigateNextLevel = { nextLevel ->
                    navController.popBackStack()
                    navController.navigate("memory_game_play/$nextLevel/normal")
                }
            )
        }

        composable("puzzle_game") {
            PuzzleGameScreen(
                onNavigateBackToApp = { navController.popBackStack() }
            )
        }
    }
}
