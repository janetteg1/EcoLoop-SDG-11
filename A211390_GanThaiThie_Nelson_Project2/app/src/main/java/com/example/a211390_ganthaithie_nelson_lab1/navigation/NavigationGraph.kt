package com.example.a211390_ganthaithie_nelson_lab1.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a211390_ganthaithie_nelson_lab1.viewmodel.AppViewModel
import com.example.a211390_ganthaithie_nelson_lab1.screens.CoverScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.HomeScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.CarpoolScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.MarketplaceScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.PostCarpoolListingScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.PostMarketListingScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.RideDetailScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.ItemDetailScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.ChatScreen
import com.example.a211390_ganthaithie_nelson_lab1.screens.ProfileScreen

private const val STANDARD_DURATION = 280
private const val SPLASH_DURATION = 650
private const val FORM_DURATION = 320

private fun standardEnter(): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(STANDARD_DURATION)
    ) { fullWidth -> fullWidth } +
        fadeIn(animationSpec = tween(STANDARD_DURATION))

private fun standardExit(): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(STANDARD_DURATION)
    ) { fullWidth -> -fullWidth / 4 } +
        fadeOut(animationSpec = tween(STANDARD_DURATION))

private fun standardPopEnter(): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(STANDARD_DURATION)
    ) { fullWidth -> -fullWidth / 4 } +
        fadeIn(animationSpec = tween(STANDARD_DURATION))

private fun standardPopExit(): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(STANDARD_DURATION)
    ) { fullWidth -> fullWidth } +
        fadeOut(animationSpec = tween(STANDARD_DURATION))

private fun formEnter(): EnterTransition =
    slideInVertically(
        animationSpec = tween(FORM_DURATION)
    ) { fullHeight -> fullHeight } +
        fadeIn(animationSpec = tween(FORM_DURATION))

private fun formExit(): ExitTransition =
    slideOutVertically(
        animationSpec = tween(FORM_DURATION)
    ) { fullHeight -> fullHeight / 2 } +
        fadeOut(animationSpec = tween(FORM_DURATION))

private fun formPopEnter(): EnterTransition =
    slideInVertically(
        animationSpec = tween(FORM_DURATION)
    ) { fullHeight -> fullHeight / 2 } +
        fadeIn(animationSpec = tween(FORM_DURATION))

private fun splashEnter(): EnterTransition =
    fadeIn(animationSpec = tween(SPLASH_DURATION)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(SPLASH_DURATION)
        )

private fun splashExit(): ExitTransition =
    fadeOut(animationSpec = tween(250))

/**
 * Navigation graph that defines all routes and screen transitions for the app
 * Handles navigation between:
 * - Splash Screen
 * - Home Screen
 * - Carpool Screen
 * - Marketplace Screen
 * - Post Listing Screen
 * - Ride Detail Screen
 * - Item Detail Screen
 * - Profile Screen
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Splash
    ) {
        // Splash Screen
        composable<AppScreen.Splash>(
            enterTransition = { splashEnter() },
            exitTransition = { splashExit() },
            popEnterTransition = { splashEnter() },
            popExitTransition = { splashExit() }
        ) {
            CoverScreen(
                onNavigateToHome = {
                    navController.navigate(AppScreen.Home) {
                        popUpTo(AppScreen.Splash) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable<AppScreen.Home>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) {
            HomeScreen(
                viewModel = viewModel,
                selectedTab = 0,
                onCarpoolClick = { navController.navigate(AppScreen.Carpool) },
                onMarketplaceClick = { navController.navigate(AppScreen.Marketplace) },
                onProfileClick = { navController.navigate(AppScreen.Profile) }
            )
        }

        // Carpool Screen
        composable<AppScreen.Carpool>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) {
            CarpoolScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPostRide = { navController.navigate(AppScreen.PostCarpool) },
                onSelectRide = { rideIndex ->
                    navController.navigate(AppScreen.RideDetail(rideIndex))
                },
                onHomeClick = {
                    navController.navigate(AppScreen.Home) {
                        launchSingleTop = true
                    }
                },
                onCarpoolClick = { },
                onMarketplaceClick = {
                    navController.navigate(AppScreen.Marketplace) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(AppScreen.Profile) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // Marketplace Screen
        composable<AppScreen.Marketplace>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) {
            MarketplaceScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPostItem = { navController.navigate(AppScreen.PostMarket) },
                onSelectItem = { itemIndex ->
                    navController.navigate(AppScreen.ItemDetail(itemIndex))
                },
                onHomeClick = {
                    navController.navigate(AppScreen.Home) {
                        launchSingleTop = true
                    }
                },
                onCarpoolClick = {
                    navController.navigate(AppScreen.Carpool) {
                        launchSingleTop = true
                    }
                },
                onMarketplaceClick = { },
                onProfileClick = {
                    navController.navigate(AppScreen.Profile) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // Post Carpool Screen
        composable<AppScreen.PostCarpool>(
            enterTransition = { formEnter() },
            exitTransition = { formExit() },
            popEnterTransition = { formPopEnter() },
            popExitTransition = { formExit() }
        ) {
            PostCarpoolListingScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }

        // Post Market Screen
        composable<AppScreen.PostMarket>(
            enterTransition = { formEnter() },
            exitTransition = { formExit() },
            popEnterTransition = { formPopEnter() },
            popExitTransition = { formExit() }
        ) {
            PostMarketListingScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }

        // Ride Detail Screen
        composable<AppScreen.RideDetail>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) { backStackEntry ->
            val rideIndex = backStackEntry.arguments?.getInt("rideIndex") ?: 0
            RideDetailScreen(
                viewModel = viewModel,
                rideIndex = rideIndex,
                onBack = { navController.popBackStack() },
                onContactClick = { threadId ->
                    navController.navigate(AppScreen.Chat(threadId))
                }
            )
        }

        // Item Detail Screen
        composable<AppScreen.ItemDetail>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) { backStackEntry ->
            val itemIndex = backStackEntry.arguments?.getInt("itemIndex") ?: 0
            ItemDetailScreen(
                viewModel = viewModel,
                itemIndex = itemIndex,
                onBack = { navController.popBackStack() },
                onContactClick = { threadId ->
                    navController.navigate(AppScreen.Chat(threadId))
                }
            )
        }

        // Chat Screen
        composable<AppScreen.Chat>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) { backStackEntry ->
            val threadId = backStackEntry.arguments?.getString("threadId") ?: ""
            ChatScreen(
                viewModel = viewModel,
                threadId = threadId,
                onBack = { navController.popBackStack() }
            )
        }

        // Profile Screen
        composable<AppScreen.Profile>(
            enterTransition = { standardEnter() },
            exitTransition = { standardExit() },
            popEnterTransition = { standardPopEnter() },
            popExitTransition = { standardPopExit() }
        ) {
            ProfileScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onHomeClick = {
                    navController.navigate(AppScreen.Home) {
                        launchSingleTop = true
                    }
                },
                onCarpoolClick = {
                    navController.navigate(AppScreen.Carpool) {
                        launchSingleTop = true
                    }
                },
                onMarketplaceClick = {
                    navController.navigate(AppScreen.Marketplace) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = { }
            )
        }
    }
}
